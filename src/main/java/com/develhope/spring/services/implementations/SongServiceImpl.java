package com.develhope.spring.services.implementations;

import com.develhope.spring.dtos.requests.SongRequestDTO;
import com.develhope.spring.dtos.responses.SongResponseDTO;
import com.develhope.spring.entities.*;
import com.develhope.spring.exceptions.*;
import com.develhope.spring.repositories.AlbumRepository;
import com.develhope.spring.repositories.GenreRepository;
import com.develhope.spring.repositories.PlaylistRepository;
import com.develhope.spring.repositories.SongRepository;
import com.develhope.spring.utils.UniversalFieldUpdater;
import com.develhope.spring.services.interfaces.MinioService;
import com.develhope.spring.services.interfaces.SongService;
import jakarta.persistence.EntityNotFoundException;
import org.apache.commons.io.FilenameUtils;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SongServiceImpl implements SongService {


    private final SongRepository songRepository;
    private final ModelMapper modelMapper;
    private final AlbumRepository albumRepository;
    private final GenreRepository genreRepository;
    private final PlaylistRepository playlistRepository;
    private final MinioService minioService;

    @Value("${minio.musicBucket}")
    private String musicBucket;

    @Autowired
    public SongServiceImpl(SongRepository songRepository, ModelMapper modelMapper, AlbumRepository albumRepository, GenreRepository genreRepository, PlaylistRepository playlistRepository, MinioService minioService) {
        this.songRepository = songRepository;
        this.modelMapper = modelMapper;
        this.albumRepository = albumRepository;
        this.genreRepository = genreRepository;
        this.playlistRepository = playlistRepository;
        this.minioService = minioService;
    }

    @Override
    public SongResponseDTO createSong(SongRequestDTO song) {

        Album album = albumRepository.findById(song.getAlbumId()).orElseThrow(
                () -> new EntityNotFoundException("Album with ID " + song.getAlbumId() + " not found in the database")
        );

        Genre genre = genreRepository.findById(song.getGenreId()).orElseThrow(
                () -> new EntityNotFoundException("Genre with ID " + song.getGenreId() + " not found in the database")
        );

        Song toSave = new Song();

        try {
            UniversalFieldUpdater.checkFieldsAndUpdate(song, toSave);
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new EntityMappingException(e.getMessage());
        }

        toSave.setAlbum(album);
        toSave.setGenre(genre);

        Song saved = songRepository.saveAndFlush(toSave);

        SongResponseDTO response = modelMapper.map(saved, SongResponseDTO.class);
        songResponseDtoSetArtistName(saved, response);

        return response;
    }

    @Override
    @Transactional
    public SongResponseDTO updateSong(Long id, SongRequestDTO song) {

        return songRepository.findById(id).map(songFound -> {
            validDtoAndUpdateSong(song, songFound);

            Song updated = songRepository.saveAndFlush(songFound);
            SongResponseDTO response = modelMapper.map(updated, SongResponseDTO.class);
            songResponseDtoSetArtistName(updated, response);

            return response;

        }).orElseThrow(() -> new EntityNotFoundException("Song with ID " + id + " not found in the database!"));
    }

    @Override
    public List<SongResponseDTO> getAllSong() {

        var songs = songRepository.findAll().stream().map(song -> {
            SongResponseDTO response = modelMapper.map(song, SongResponseDTO.class);
            songResponseDtoSetArtistName(song, response);

            return response;

        }).collect(Collectors.toList());

        if (songs.isEmpty()) {
            throw new EmptyResultException("No songs found in the database.");

        } else {
            return songs;
        }
    }

    @Transactional
    @Override
    public String[] uploadSongs(MultipartFile[] files, Long[] songIDS) {

        long maxSize = 209715200L;
        int maxSizeMB = (int) maxSize / (1024 * 1024);

        if (files.length != songIDS.length) throw new MultiUploadFailedException(
                "[Upload failed]  The number of file(s) does not correspond to the number of identifiers! Files cannot be uploaded."
        );

        String[] uploadedLinks = new String[files.length];

        for (int i = 0; i < files.length; i++) {

            var file = files[i];
            var songID = songIDS[i];

            if (songID < 0) {
                uploadedLinks[i] = "[Song upload failed] File: " +
                        file.getOriginalFilename() + "(ID < 0, current value: " + songID + ")";

                continue;
            }

            String extension = Objects.requireNonNull(FilenameUtils.getExtension(file.getOriginalFilename())).toLowerCase();

            if (!extension.equals("mp3") && !extension.equals("wav") &&
                    !extension.equals("flac") && !extension.equals("m4a")) {
                uploadedLinks[i] = "[Song upload failed] File: " +
                        file.getOriginalFilename() + "(unsupported format(Available formats: .mp3 / .wav / .flac / .m4a))";

                continue;
            }

            if (file.getSize() > maxSize) {
                uploadedLinks[i] = "[Song upload failed] File: " + file.getOriginalFilename() +
                        "File too large. Max. size = " + maxSizeMB + "MB" + "Now: " + file.getSize() / (1024 * 1024) + "MB";
                continue;
            }

            Optional<Song> songOptional = songRepository.findById(songID);

            if (songOptional.isEmpty()){
                uploadedLinks[i] = "[Song upload failed] Song with id " +
                        songID + " not found in the database, file " + file.getOriginalFilename() + " is not uploaded";

                continue;
            }

            Song song = songOptional.get();

            String albumName = song.getAlbum().getTitle();
            String songNAme = song.getTitle();
            String artistName = song.getAlbum().getArtist().getArtistName();

            String destinationFolderName = artistName + "_" + albumName;
            String newFileName = artistName + "_" + songNAme + "_" + UUID.randomUUID();

            Map<String, String> uploaded = minioService.uploadFile(file, newFileName, destinationFolderName, musicBucket);

            String fullLink = uploaded.get("fullLink");
            String objectStorageFileName = uploaded.get("objectName");

            song.setLink_audio(fullLink);
            song.setObjectStorageFileName(objectStorageFileName);


            uploadedLinks[i] = "Uploaded file url: " + fullLink;
        }

        return uploadedLinks;
    }

    @Override
    @Transactional
    public void deleteSongFromMinioStorage(Long songID) {

        Song song = songRepository.findById(songID).orElseThrow(
                () -> new EntityNotFoundException("[Song delete failed] Song with ID " + songID + " not Found!")
        );

        String fileToDelete = song.getObjectStorageFileName();

        boolean deleted = minioService.deleteFile(musicBucket, fileToDelete);

        if (deleted) {
            song.setObjectStorageFileName(null);
            song.setLink_audio(null);
            songRepository.saveAndFlush(song);
        }
    }

    @Override
    public SongResponseDTO findSongById(long id) {

        return songRepository.findById(id).map(songFound -> {
            SongResponseDTO response = modelMapper.map(songFound, SongResponseDTO.class);
            songResponseDtoSetArtistName(songFound, response);

            return response;

        }).orElseThrow(() -> new EntityNotFoundException("Song with ID " + id + " not found in the database!"));
    }

    @Override
    @Transactional
    public SongResponseDTO deleteSongById(Long id) {
        return songRepository.findById(id).map(songFound -> {

            List<Playlist> playlists = playlistRepository.findAll();
            playlists.forEach(playlist -> playlist.getSongs().remove(songFound));
            playlistRepository.saveAll(playlists);

            songRepository.deleteById(id);

            SongResponseDTO deleted = modelMapper.map(songFound, SongResponseDTO.class);
            songResponseDtoSetArtistName(songFound, deleted);

            return deleted;

        }).orElseThrow(() -> new EntityNotFoundException("Song with ID " + id + " not found in the database"));
    }


    @Override
    public void deleteAllSongs() {
        songRepository.deleteAll();
    }

    private void validDtoAndUpdateSong(SongRequestDTO song, Song songFound) {
        boolean anyFieldSet = false;

        if (song.getTitle() != null && !song.getTitle().isBlank()) {
            songFound.setTitle(songFound.getTitle());
            anyFieldSet = true;
        }

        if (song.getAlbumId() != null && song.getAlbumId() > 0) {
            Optional<Album> album = albumRepository.findById(song.getAlbumId());
            if (album.isEmpty()) {
                throw new EntityNotFoundException("Album with ID " +
                        song.getAlbumId() + " not found in database");
            }
            songFound.setAlbum(album.get());
            anyFieldSet = true;
        }

        if (song.getGenreId() != null && song.getGenreId() > 0) {
            Optional<Genre> genre = genreRepository.findById(song.getGenreId());
            if (genre.isEmpty()) {
                throw new EntityNotFoundException("Genre with ID " +
                        song.getGenreId() + " not found in database");
            }
            songFound.setGenre(genre.get());
            anyFieldSet = true;
        }

        if (song.getYear_release() > 1000) {
            songFound.setYear_release(song.getYear_release());
            anyFieldSet = true;
        }

        if (song.getDuration_time() != null) {
            songFound.setDuration_time(song.getDuration_time());
            anyFieldSet = true;
        }

        if (!anyFieldSet) {
            throw new IllegalArgumentException("[Update error] all fields are either null or blank, or not valid. " +
                    "At least one field must be filled in with a valid value!");
        }
    }

    private void songResponseDtoSetArtistName(Song song, SongResponseDTO responseDTO) {
        String artistName = song.getAlbum().getArtist().getArtistName();
        responseDTO.setArtistName(artistName);
    }

}
