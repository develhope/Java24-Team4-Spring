package com.develhope.spring.services.implementations;

import com.develhope.spring.dtos.requests.AlbumRequestDTO;
import com.develhope.spring.dtos.responses.AlbumResponseDTO;
import com.develhope.spring.entities.Album;
import com.develhope.spring.entities.Artist;
import com.develhope.spring.exceptions.EmptyResultException;
import com.develhope.spring.repositories.AlbumRepository;
import com.develhope.spring.repositories.ArtistRepository;
import com.develhope.spring.services.interfaces.AlbumService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AlbumServiceImpl implements AlbumService {

    private final ModelMapper modelMapper;
    private final AlbumRepository albumRepository;
    private final ArtistRepository artistRepository;

    @Autowired
    public AlbumServiceImpl(ModelMapper modelMapper, AlbumRepository albumRepository, ArtistRepository artistRepository) {
        this.modelMapper = modelMapper;
        this.albumRepository = albumRepository;
        this.artistRepository = artistRepository;
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
}