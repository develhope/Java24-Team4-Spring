package com.develhope.spring.services.interfaces;

import com.develhope.spring.dtos.requests.SongRequestDTO;
import com.develhope.spring.dtos.responses.SongResponseDTO;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface SongService {

    SongResponseDTO createSong(SongRequestDTO song);

    List<SongResponseDTO> getAllSong();

    @Transactional
    String[] uploadSongs(MultipartFile[] files, Long[] songIDS);

    void deleteSongFromMinioStorage(Long userID);

    SongResponseDTO findSongById(long id);

    SongResponseDTO updateSong(Long id, SongRequestDTO song);

    SongResponseDTO deleteSongById(Long id);

    void deleteAllSongs();

}
