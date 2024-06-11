package com.develhope.spring.services.implementations;

import com.develhope.spring.dtos.requests.LikesSongRequestDTO;
import com.develhope.spring.dtos.responses.LikesSongResponseDTO;
import com.develhope.spring.entities.LikesSongs;
import com.develhope.spring.entities.Listener;
import com.develhope.spring.entities.Song;
import com.develhope.spring.repositories.LikesSongRepository;
import com.develhope.spring.repositories.ListenerRepository;
import com.develhope.spring.repositories.SongRepository;
import com.develhope.spring.services.interfaces.LikesSongService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LikesSongServiceImpl implements LikesSongService {

    private final LikesSongRepository likesSongRepository;
    private final SongRepository songRepository;
    private final ListenerRepository listenerRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public LikesSongServiceImpl(LikesSongRepository likesSongsRepository, SongRepository songRepository, ListenerRepository listenerRepository, ModelMapper modelMapper) {
        this.likesSongRepository = likesSongsRepository;
        this.songRepository = songRepository;
        this.listenerRepository = listenerRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Optional<LikesSongResponseDTO> getLikeById(Long id) {
        Optional<LikesSongs> like = likesSongRepository.findById(id);
        if (like.isPresent()) {
            LikesSongResponseDTO responseDTO = modelMapper.map(like.get(), LikesSongResponseDTO.class);
            return Optional.of(responseDTO);
        }
        return Optional.empty();
    }

    @Override
    public Optional<LikesSongResponseDTO> createLike(LikesSongRequestDTO requestDTO) {
        Song song = songRepository.findById(requestDTO.getSongId()).orElseThrow(() -> new IllegalArgumentException("Song not found"));
        Listener listener = listenerRepository.findById(requestDTO.getListenerId()).orElseThrow(() -> new IllegalArgumentException("Listener not found"));

        LikesSongs like = new LikesSongs();
        like.setSong(song);
        like.setListener(listener);
        like.setIsLiked(requestDTO.getIsLiked());

        LikesSongs savedLike = likesSongRepository.saveAndFlush(like);
        return Optional.ofNullable(modelMapper.map(savedLike, LikesSongResponseDTO.class));
    }

    @Override
    public Optional<LikesSongResponseDTO> updateLike(Long likeId, LikesSongRequestDTO request) {
        Optional<LikesSongs> optionalLike = likesSongRepository.findById(likeId);
        if (optionalLike.isPresent()) {
            LikesSongs existingLike = optionalLike.get();
            Song song = songRepository.findById(request.getSongId()).orElseThrow(() -> new IllegalArgumentException("Song not found"));
            Listener listener = listenerRepository.findById(request.getListenerId()).orElseThrow(() -> new IllegalArgumentException("Listener not found"));

            existingLike.setSong(song);
            existingLike.setListener(listener);
            existingLike.setIsLiked(request.getIsLiked());

            LikesSongs updatedLike = likesSongRepository.saveAndFlush(existingLike);
            return Optional.of(modelMapper.map(updatedLike, LikesSongResponseDTO.class));
        }
        return Optional.empty();
    }

    @Override
    public void deleteLikeById(Long id) {
        likesSongRepository.deleteById(id);
    }

    @Override
    public List<LikesSongResponseDTO> getLikesByListenerId(Long listenerId) {
        return null;
    }

    @Override
    public List<LikesSongResponseDTO> getLikesBySongId(Long songId) {
        return null;
    }
}
