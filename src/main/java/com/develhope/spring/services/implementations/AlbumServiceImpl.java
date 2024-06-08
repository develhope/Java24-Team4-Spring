package com.develhope.spring.services.implementations;

import com.develhope.spring.entities.Album;
import com.develhope.spring.repositories.AlbumRepository;
import com.develhope.spring.services.interfaces.AlbumService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlbumServiceImpl implements AlbumService {
    private final ModelMapper modelMapper;
    @Autowired
    private AlbumRepository albumRepository;

    public AlbumServiceImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Album createAlbum(Album album) {
        return albumRepository.saveAndFlush(album);
    }

    public List<Album> getAllAlbums() {
        return albumRepository.findAll();
    }

    public Optional<Album> albumById(Long id) {
        return albumRepository.findById(id);
    }

    public Album updateAlbum(Long id, Album album) {
        return albumRepository.saveAndFlush(album);
    }

    public void albumDelete(Long id) {
        albumRepository.deleteById(id);
    }
}