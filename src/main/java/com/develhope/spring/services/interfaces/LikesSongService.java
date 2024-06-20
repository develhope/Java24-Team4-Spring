package com.develhope.spring.services.interfaces;

import com.develhope.spring.dtos.requests.LikesSongRequestDTO;
import com.develhope.spring.dtos.responses.LikesSongResponseDTO;

import java.util.List;
import java.util.Optional;

public interface LikesSongService {

    Optional<LikesSongResponseDTO> getLikeById(Long id);

    Optional<LikesSongResponseDTO> createLike(LikesSongRequestDTO requestDTO);

    Optional<LikesSongResponseDTO> updateLike(Long likeId, LikesSongRequestDTO request);

    void deleteLikeById(Long id);

    void deleteAllLikes();

    List<LikesSongResponseDTO> getLikesByListenerId(Long listenerId);

    List<LikesSongResponseDTO> getLikesBySongId(Long songId);
}

