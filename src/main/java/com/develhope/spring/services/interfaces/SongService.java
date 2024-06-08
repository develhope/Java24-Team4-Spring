package com.develhope.spring.services.interfaces;

import com.develhope.spring.dtos.requests.SongRequestDTO;
import com.develhope.spring.dtos.responses.SongResponseDTO;
import com.develhope.spring.entities.Song;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface SongService {

    SongResponseDTO createSong(SongRequestDTO song);

    List<SongResponseDTO> getAllSong();

    SongResponseDTO findSongById(long id);

    SongResponseDTO updateSong(Long id, SongRequestDTO song);

    SongResponseDTO deleteSongById(Long id);

    void delteteAllSongs();
}
