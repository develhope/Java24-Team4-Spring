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
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        throw new EntityNotFoundException("Like with id " + " not found!");
    }

    @Override
    @Transactional
    public Optional<LikesSongResponseDTO> createLike(LikesSongRequestDTO requestDTO) {
        Song song = songRepository.findById(requestDTO.getSongId()).orElseThrow(() -> new EntityNotFoundException("Song not found"));
        Listener listener = listenerRepository.findById(requestDTO.getListenerId()).orElseThrow(() -> new EntityNotFoundException("Listener not found"));

        LikesSongs like = modelMapper.map(requestDTO,LikesSongs.class);
        like.setSong(song);
        like.setListener(listener);
        like.setIsLiked(requestDTO.getIsLiked());

        LikesSongs savedLike = likesSongRepository.saveAndFlush(like);
        return Optional.ofNullable(modelMapper.map(savedLike, LikesSongResponseDTO.class));
    }

    @Override
    @Transactional
    public Optional<LikesSongResponseDTO> updateLike(Long likeId, LikesSongRequestDTO request) {
        Optional<LikesSongs> optionalLike = likesSongRepository.findById(likeId);
        if (optionalLike.isPresent()) {
            LikesSongs existingLike = optionalLike.get();
            Song song = songRepository.findById(request.getSongId()).orElse(null);
            if (song == null) {
                throw new EntityNotFoundException("Song not found");
            }
            Listener listener = listenerRepository.findById(request.getListenerId()).orElse(null);
            if (listener == null) {
                throw new EntityNotFoundException("Listener not found");
            }

            existingLike.setSong(song);
            existingLike.setListener(listener);
            existingLike.setIsLiked(request.getIsLiked());

            LikesSongs updatedLike = likesSongRepository.saveAndFlush(existingLike);
            return Optional.of(modelMapper.map(updatedLike, LikesSongResponseDTO.class));
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public void deleteLikeById(Long id) {
        if (likesSongRepository.existsById(id)){
            likesSongRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Like with id " + id + " not found");
        }

    }

    @Override
    public void deleteAllLikes(){
        likesSongRepository.deleteAll();
    }

    @Override
    public List<LikesSongResponseDTO> getLikesByListenerId(Long listenerId) {
        List<LikesSongs> likesSongs = likesSongRepository.findByListenerId(listenerId);
        if (likesSongs.isEmpty()){
            throw new EntityNotFoundException("No likes found for listener with that id");
        }
        return likesSongs.stream()
                .map(likesSongs1 -> modelMapper.map(likesSongs1, LikesSongResponseDTO.class))
                .collect(Collectors.toList());
    }
    @Override
    public List<LikesSongResponseDTO> getLikesBySongId(Long songId) {
        List<LikesSongs> likesSongs = likesSongRepository.findBySongId(songId);
        if (likesSongs.isEmpty()){
            throw new EntityNotFoundException("No likes found for song with that id");

        }
        return likesSongs.stream()
                .map(likesSongs1 -> modelMapper.map(likesSongs1, LikesSongResponseDTO.class))
                .collect(Collectors.toList());
    }
}