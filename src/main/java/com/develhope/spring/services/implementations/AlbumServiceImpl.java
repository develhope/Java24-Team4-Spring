package com.develhope.spring.services.implementations;

import com.develhope.spring.dtos.requests.AlbumRequestDTO;
import com.develhope.spring.dtos.responses.AlbumResponseDTO;
import com.develhope.spring.entities.Album;
import com.develhope.spring.entities.Artist;
import com.develhope.spring.repositories.AlbumRepository;
import com.develhope.spring.repositories.ArtistRepository;
import com.develhope.spring.services.interfaces.AlbumService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public AlbumResponseDTO createAlbum(AlbumRequestDTO albumRequestDTO) {
        Optional<Artist> artist = artistRepository.findById(albumRequestDTO.getArtistId());
        if (artist.isPresent()) {
            Album album = modelMapper.map(albumRequestDTO, Album.class);
            album.setArtist(artist.get());
            Album savedAlbum = albumRepository.saveAndFlush(album);
            return modelMapper.map(savedAlbum, AlbumResponseDTO.class);
        } else {
            throw new EntityNotFoundException("Artist not found with id: " + albumRequestDTO.getArtistId());
        }
    }

    @Override
    public List<AlbumResponseDTO> getAllAlbums(){
        return albumRepository.findAll().stream()
                .map(album -> modelMapper.map(album, AlbumResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<AlbumResponseDTO> albumById(Long id){
        return albumRepository.findById(id)
                .map(album -> modelMapper.map(album, AlbumResponseDTO.class));
    }

    @Override
    public AlbumResponseDTO updateAlbum(Long id, AlbumRequestDTO albumRequestDTO){
        Optional<Album> existingAlbumOpt = albumRepository.findById(id);
        if(existingAlbumOpt.isPresent()) {
            Album existingAlbum = existingAlbumOpt.get();
            modelMapper.map(albumRequestDTO, existingAlbum);
            Album updatedAlbum = albumRepository.saveAndFlush(existingAlbum);
            return modelMapper.map(updatedAlbum, AlbumResponseDTO.class);
        } else {
            throw new EntityNotFoundException("Album not found with id: " + id);
        }
    }

    @Override
    public void albumDelete(Long id){
        albumRepository.deleteById(id);
    }
}