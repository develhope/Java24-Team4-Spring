package com.develhope.spring.services.interfaces;

import com.develhope.spring.dtos.requests.AlbumRequestDTO;
import com.develhope.spring.dtos.responses.AlbumResponseDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AlbumService {
    AlbumResponseDTO createAlbum(AlbumRequestDTO albumRequestDTO);
    List<AlbumResponseDTO> getAllAlbums();
    AlbumResponseDTO getAlbumById(Long id);
    AlbumResponseDTO updateAlbum(Long id, AlbumRequestDTO albumRequestDTO);
    public AlbumResponseDTO deleteAlbumById(Long id);
    String uploadAlbumImage(MultipartFile file, Long AlbumID);
    void deleteAlbumImage (Long userID);
}