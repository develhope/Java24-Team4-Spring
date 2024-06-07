package com.develhope.spring.services.interfaces;

import com.develhope.spring.dtos.responses.SongResponseDTO;
import com.develhope.spring.entities.Song;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.List;
import java.util.Optional;
@Service
public interface SongService {
    Optional<SongResponseDTO> createSong(SongResponseDTO songResponseDTO, Long SongId);
    List<Song> getAllSong();
    Optional<Song> findSongById(long id);
    Optional<SongResponseDTO> updateSong(Long id, SongResponseDTO updateSongDTO);
   Optional<Song> deleteSongById(Long id);
   void delteteAllSongs();
}
