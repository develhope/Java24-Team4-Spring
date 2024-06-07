package com.develhope.spring.services;

import com.develhope.spring.dtos.requests.PlaylistRequestDTO;
import com.develhope.spring.dtos.responses.PlaylistResponseDTO;

import java.util.List;
import java.util.Optional;

public interface PlaylistService {
    Optional<PlaylistResponseDTO> getPlaylistById(Long id);
    List<PlaylistResponseDTO> getAllPlaylists();
    Optional<PlaylistResponseDTO> createPlaylist(PlaylistRequestDTO request);
    Optional<PlaylistResponseDTO> updatePlaylist(Long id, PlaylistRequestDTO request);
    Optional<PlaylistResponseDTO> deletePlaylistById(Long id);
    void deleteAllPlaylists();
}
