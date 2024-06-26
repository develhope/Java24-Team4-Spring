package com.develhope.spring.services.implementations;

import com.develhope.spring.dtos.requests.AlbumRequestDTO;
import com.develhope.spring.dtos.responses.AlbumResponseDTO;
import com.develhope.spring.entities.Album;
import com.develhope.spring.entities.Artist;
import com.develhope.spring.entities.User;
import com.develhope.spring.exceptions.EmptyResultException;
import com.develhope.spring.exceptions.MinIOFileUploadException;
import com.develhope.spring.exceptions.UnsupportedFileFormatException;
import com.develhope.spring.repositories.AlbumRepository;
import com.develhope.spring.repositories.ArtistRepository;
import com.develhope.spring.services.interfaces.AlbumService;
import com.develhope.spring.services.interfaces.MinioService;
import jakarta.persistence.EntityNotFoundException;
import org.apache.commons.io.FilenameUtils;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AlbumServiceImpl implements AlbumService {

    private final MinioService minioService;
    private final ModelMapper modelMapper;
    private final AlbumRepository albumRepository;
    private final ArtistRepository artistRepository;
    @Value("${minio.profileDataBucket}")
    private String profileDataBucket;

    @Autowired
    public AlbumServiceImpl(MinioService minioService, ModelMapper modelMapper, AlbumRepository albumRepository, ArtistRepository artistRepository) {
        this.modelMapper = modelMapper;
        this.albumRepository = albumRepository;
        this.artistRepository = artistRepository;
        this.minioService = minioService;
    }

    @Override
    @Transactional
    public AlbumResponseDTO createAlbum(AlbumRequestDTO albumRequestDTO) {
        Optional<Artist> artist = artistRepository.findById(albumRequestDTO.getArtistId());

        if (artist.isPresent()) {
            Album album = modelMapper.map(albumRequestDTO, Album.class);
            album.setArtist(artist.get());
            Album savedAlbum = albumRepository.saveAndFlush(album);

            return modelMapper.map(savedAlbum, AlbumResponseDTO.class);

        } else {
            throw new EntityNotFoundException("[Creation error] Artist not found with id: " + albumRequestDTO.getArtistId());
        }
    }

    @Override
    public List<AlbumResponseDTO> getAllAlbums() {

        var albumsList = albumRepository.findAll().stream()
                .map(album -> modelMapper.map(album, AlbumResponseDTO.class))
                .collect(Collectors.toList());

        if (albumsList.isEmpty()) {
            throw new EmptyResultException("[Search error] No albums found in the database.");
        }

        return albumsList;
    }

    @Override
    public AlbumResponseDTO getAlbumById(Long id) {
        return albumRepository.findById(id)

                .map(album -> modelMapper.map(album, AlbumResponseDTO.class)).orElseThrow(() -> new EntityNotFoundException("[Search error] Album with id " +
                        id + " not found in the database."));
    }

    @Override
    @Transactional
    public AlbumResponseDTO updateAlbum(Long id, AlbumRequestDTO albumRequestDTO) {
        Optional<Album> existingAlbumOpt = albumRepository.findById(id);
        if (existingAlbumOpt.isPresent()) {
            Album existingAlbum = existingAlbumOpt.get();
            modelMapper.map(albumRequestDTO, existingAlbum);
            Album updatedAlbum = albumRepository.saveAndFlush(existingAlbum);
            return modelMapper.map(updatedAlbum, AlbumResponseDTO.class);
        } else {
            throw new EntityNotFoundException("[Update error] Album not found with id: " + id);
        }
    }

    @Override
    @Transactional
    public AlbumResponseDTO deleteAlbumById(Long id) {

        return albumRepository.findById(id).map(album -> {
            albumRepository.deleteById(id);
            AlbumResponseDTO deleted = modelMapper.map(album, AlbumResponseDTO.class);

            return deleted;

        }).orElseThrow(() -> new EntityNotFoundException("[Delete error] Album with id " +
                id + " non found in the database"));
    }
    @Override
    public String uploadAlbumImage(MultipartFile file, Long albumID)  {

        Optional<Album> albumOptional = albumRepository.findById(albumID);

        if (albumOptional.isEmpty()) {
            throw new EntityNotFoundException("[Album image upload failed] Album with ID " + albumID + " not Found!");
        }

        long maxSize = 1048576L; // 1MB
        int maxSizeMB = (int) maxSize / (1024 * 1024);

        String extension = Objects.requireNonNull(FilenameUtils.getExtension(file.getOriginalFilename())).toLowerCase();

        if (!extension.equals("jpg") && !extension.equals("jpeg") && !extension.equals("png") && !extension.equals("gif")) {
            throw new UnsupportedFileFormatException(
                    "[Album image upload failed] unsupported format(Available formats: .jpg / .jpeg / .png / .gif)"
            );
        }
        if (file.getSize() > maxSize) {
            try {
                throw new FileSizeLimitExceededException(
                        "File too large. Max. size = " + maxSizeMB + "MB", file.getSize(), maxSize
                );
            } catch (FileSizeLimitExceededException e) {
                throw new MinIOFileUploadException(e.getMessage());
            }
        }

        String destinationFolderName = "album_" + albumID + "_data/albumImages";
        String newFileName = "album_" + albumID + "_albumImg";

        Map<String, String> uploaded = minioService.uploadFile(file, newFileName, destinationFolderName, profileDataBucket);
        String fullLink = uploaded.get("fullLink");
        String objectStorageFileName = uploaded.get("objectName");

        Album album  = albumOptional.get();
        album.setCover_link(fullLink);
        album.setPhotoObjectStorageName(objectStorageFileName);
        albumRepository.saveAndFlush(album);

        return "Uploaded file url:" + fullLink;
    }
    @Override
    public void deleteAlbumImage(Long albumID) {
        Optional<Album> albumOptional = albumRepository.findById(albumID);

        if (albumOptional.isEmpty()) {
            throw new EntityNotFoundException("[Profile image delete failed] User with ID " + albumID + " not Found!");
        }

        Album album = albumOptional.get();
        String fileToDelete = album.getPhotoObjectStorageName();

        boolean deleted = minioService.deleteFile(profileDataBucket, fileToDelete);

        if (deleted) {
            album.setPhotoObjectStorageName(null);
            album.setCover_link(null);
            albumRepository.saveAndFlush(album);
        }
    }
}