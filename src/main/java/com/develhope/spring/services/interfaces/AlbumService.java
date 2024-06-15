package com.develhope.spring.services.interfaces;

import com.develhope.spring.dtos.requests.AlbumRequestDTO;
import com.develhope.spring.dtos.responses.AlbumResponseDTO;
import com.develhope.spring.entities.Album;

import java.util.List;
import java.util.Optional;

public interface AlbumService {
    AlbumResponseDTO createAlbum(AlbumRequestDTO albumRequestDTO);
    List<AlbumResponseDTO> getAllAlbums();
    Optional<AlbumResponseDTO> albumById(Long id);
    AlbumResponseDTO updateAlbum(Long id, AlbumRequestDTO albumRequestDTO);
    public void albumDelete(Long id);


}