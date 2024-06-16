package com.develhope.spring.services.implementations;

import com.develhope.spring.dtos.requests.SongRequestDTO;
import com.develhope.spring.dtos.responses.SongResponseDTO;
import com.develhope.spring.entities.Album;
import com.develhope.spring.entities.Genre;
import com.develhope.spring.entities.Song;
import com.develhope.spring.exceptions.EmptyResultException;
import com.develhope.spring.repositories.AlbumRepository;
import com.develhope.spring.repositories.GenreRepository;
import com.develhope.spring.repositories.SongRepository;
import com.develhope.spring.services.interfaces.SongService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SongServiceImpl implements SongService {


    private final SongRepository songRepository;
    private final ModelMapper modelMapper;
    private final AlbumRepository albumRepository;
    private final GenreRepository genreRepository;

    @Autowired
    public SongServiceImpl(SongRepository songRepository, ModelMapper modelMapper, AlbumRepository albumRepository, GenreRepository genreRepository) {
        this.songRepository = songRepository;
        this.modelMapper = modelMapper;
        this.albumRepository = albumRepository;
        this.genreRepository = genreRepository;
    }

    @Override
    public SongResponseDTO createSong(SongRequestDTO song) {

        Optional<Album> album = albumRepository.findById(song.getAlbumId());
        Optional<Genre> genre = genreRepository.findById(song.getGenreId());

        if (album.isEmpty()) {
            throw new EntityNotFoundException("Album with ID " + song.getAlbumId() + " not found in the database");
        }

        if (genre.isEmpty()) {
            throw new EntityNotFoundException("Genre with ID " + song.getGenreId() + " not found in the database");
        }

        Song toSave = modelMapper.map(song, Song.class);

        toSave.setAlbum(album.get());
        toSave.setGenre(genre.get());

        Song saved = songRepository.saveAndFlush(toSave);

        SongResponseDTO response = modelMapper.map(saved, SongResponseDTO.class);

        return response;

    }

    @Override
    public SongResponseDTO updateSong(Long id, SongRequestDTO song) {

        return songRepository.findById(id).map(songFound -> {
            validDtoAndUpdateSong(song, songFound);

            Song updated = songRepository.saveAndFlush(songFound);
            SongResponseDTO response = modelMapper.map(updated, SongResponseDTO.class);

            return response;

        }).orElseThrow(() -> new EntityNotFoundException("Song with ID " + id + " not found in the database!"));
    }

    @Override
    public List<SongResponseDTO> getAllSong() {

        var songs = songRepository.findAll().stream().map(song -> {
            SongResponseDTO response = modelMapper.map(song, SongResponseDTO.class);

            return response;

        }).collect(Collectors.toList());

        if (songs.isEmpty()) {
            throw new EmptyResultException("No songs found in the database.");

        } else {
            return songs;
        }
    }


    @Override
    public SongResponseDTO findSongById(long id) {

        return songRepository.findById(id).map(songFound -> {
            SongResponseDTO response = modelMapper.map(songFound, SongResponseDTO.class);

            return response;

        }).orElseThrow(() -> new EntityNotFoundException("Song with ID " + id + " not found in the database!"));
    }

    @Override
    public SongResponseDTO deleteSongById(Long id) {

        return songRepository.findById(id).map(songFound -> {
            songRepository.deleteById(id);
            SongResponseDTO deleted = modelMapper.map(songFound, SongResponseDTO.class);

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

        if (song.getLink_audio() != null && !song.getLink_audio().isBlank()) {
            songFound.setLink_audio(song.getLink_audio());
            anyFieldSet = true;
        }

        if (!anyFieldSet) {
            throw new IllegalArgumentException("Update error: all fields are either null or blank, or not valid. " +
                    "At least one field must be filled in with a valid value!");
        }
    }


}
