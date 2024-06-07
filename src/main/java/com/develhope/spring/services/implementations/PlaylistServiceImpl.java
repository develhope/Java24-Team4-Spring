package com.develhope.spring.services.implementations;

import com.develhope.spring.dtos.requests.PlaylistRequestDTO;
import com.develhope.spring.dtos.responses.PlaylistResponseDTO;
import com.develhope.spring.entities.Listener;
import com.develhope.spring.entities.Playlist;
import com.develhope.spring.entities.Song;
import com.develhope.spring.repositories.ListenerRepository;
import com.develhope.spring.repositories.PlaylistRepository;
import com.develhope.spring.repositories.SongRepository;
import com.develhope.spring.services.PlaylistService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlaylistServiceImpl implements PlaylistService {

    private final PlaylistRepository playlistRepository;
    private final ListenerRepository listenerRepository;
    private final SongRepository songRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public PlaylistServiceImpl(
            PlaylistRepository playlistRepository,
            ListenerRepository listenerRepository,
            SongRepository songRepository,
            ModelMapper modelMapper
    ) {
        this.playlistRepository = playlistRepository;
        this.listenerRepository = listenerRepository;
        this.songRepository = songRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Optional<PlaylistResponseDTO> getPlaylistById(Long id) {
        return playlistRepository.findById(id)
                .map(playlist -> modelMapper.map(playlist, PlaylistResponseDTO.class));
    }

    @Override
    public List<PlaylistResponseDTO> getAllPlaylists() {
        return playlistRepository.findAll()
                .stream()
                .map(playlist -> modelMapper.map(playlist, PlaylistResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<PlaylistResponseDTO> createPlaylist(PlaylistRequestDTO request) {
        Optional<Listener> listener = listenerRepository.findById(request.getListenerId());
        List<Song> songs = songRepository.findAllById(request.getSongIds());

        if (listener.isPresent()) {
            Playlist playlist = modelMapper.map(request, Playlist.class);
            playlist.setListener(listener.get());
            playlist.setSongs(songs);

            Playlist savedPlaylist = playlistRepository.saveAndFlush(playlist);
            return Optional.of(modelMapper.map(savedPlaylist, PlaylistResponseDTO.class));
        }

        return Optional.empty();
    }

    @Override
    public Optional<PlaylistResponseDTO> updatePlaylist(Long id, PlaylistRequestDTO request) {
        Optional<Listener> listener = listenerRepository.findById(request.getListenerId());
        List<Song> songs = songRepository.findAllById(request.getSongIds());

        return playlistRepository.findById(id)
                .map(existingPlaylist -> {
                    modelMapper.map(request, existingPlaylist);
                    existingPlaylist.setListener(listener.orElse(null));
                    existingPlaylist.setSongs(songs);

                    Playlist updatedPlaylist = playlistRepository.saveAndFlush(existingPlaylist);
                    return modelMapper.map(updatedPlaylist, PlaylistResponseDTO.class);
                });
    }

    @Override
    public Optional<PlaylistResponseDTO> deletePlaylistById(Long id) {
        return playlistRepository.findById(id)
                .map(playlist -> {
                    playlistRepository.deleteById(id);
                    return modelMapper.map(playlist, PlaylistResponseDTO.class);
                });
    }

    @Override
    public void deleteAllPlaylists() {
        playlistRepository.deleteAll();
    }
}
