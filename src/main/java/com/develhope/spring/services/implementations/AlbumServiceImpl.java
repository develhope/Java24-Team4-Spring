package com.develhope.spring.services.implementations;

import com.develhope.spring.dtos.requests.AlbumRequestDTO;
import com.develhope.spring.dtos.responses.AlbumResponseDTO;
import com.develhope.spring.entities.Album;
import com.develhope.spring.repositories.AlbumRepository;
import com.develhope.spring.services.interfaces.AlbumService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AlbumServiceImpl implements AlbumService {

    private final AlbumRepository albumRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public AlbumServiceImpl(AlbumRepository albumRepository, ModelMapper modelMapper) {
        this.albumRepository = albumRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Optional<AlbumResponseDTO> getAlbumById(Long id) {
        return albumRepository.findById(id).map(album -> modelMapper.map(album, AlbumResponseDTO.class));
    }

    @Override
    public List<AlbumResponseDTO> getAllAlbums() {
        return albumRepository.findAll().stream()
                .map(album -> modelMapper.map(album, AlbumResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<AlbumResponseDTO> createAlbum(AlbumRequestDTO request) {
        Album album = modelMapper.map(request, Album.class);
        Album savedAlbum = albumRepository.saveAndFlush(album);
        return Optional.of(modelMapper.map(savedAlbum, AlbumResponseDTO.class));
    }

    @Override
    public Optional<AlbumResponseDTO> updateAlbum(Long id, AlbumRequestDTO request) {
        return albumRepository.findById(id).map(existingAlbum -> {
            modelMapper.map(request, existingAlbum);
            Album updatedAlbum = albumRepository.saveAndFlush(existingAlbum);
            return modelMapper.map(updatedAlbum, AlbumResponseDTO.class);
        });
    }

    @Override
    public Optional<Album> deleteAlbumById(Long id) {
        return albumRepository.findById(id).map(album -> {
            albumRepository.deleteById(id);
            return album;
        });
    }

    @Override
    public void deleteAllAlbums() {
        albumRepository.deleteAll();
    }
}
