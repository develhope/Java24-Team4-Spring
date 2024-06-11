package com.develhope.spring.services.interfaces;

import com.develhope.spring.dtos.requests.AlbumRequestDTO;
import com.develhope.spring.dtos.responses.AlbumResponseDTO;
import com.develhope.spring.entities.Album;

import java.util.List;
import java.util.Optional;

public interface AlbumService {

    Optional<AlbumResponseDTO> getAlbumById(Long id);

    List<AlbumResponseDTO> getAllAlbums();

    Optional<AlbumResponseDTO> createAlbum(AlbumRequestDTO request);

    Optional<AlbumResponseDTO> updateAlbum(Long id, AlbumRequestDTO request);

    Optional<Album> deleteAlbumById(Long id);

    void deleteAllAlbums();
}
