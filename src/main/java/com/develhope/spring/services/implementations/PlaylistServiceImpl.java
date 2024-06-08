package com.develhope.spring.services.implementations;

import com.develhope.spring.dtos.requests.PlaylistRequestDTO;
import com.develhope.spring.dtos.responses.PlaylistResponseDTO;
import com.develhope.spring.entities.Listener;
import com.develhope.spring.entities.Playlist;
import com.develhope.spring.entities.Song;
import com.develhope.spring.exceptions.EmptyResultException;
import com.develhope.spring.exceptions.EmptySongsListOnUpdateException;
import com.develhope.spring.repositories.ListenerRepository;
import com.develhope.spring.repositories.PlaylistRepository;
import com.develhope.spring.repositories.SongRepository;
import com.develhope.spring.services.UniversalFieldUpdater;
import com.develhope.spring.services.interfaces.PlaylistService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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
    public PlaylistResponseDTO getPlaylistById(Long id) {

        return playlistRepository.findById(id)
                .map(playlist -> {
                    var playlistDto = modelMapper.map(playlist, PlaylistResponseDTO.class);

                    Long listenerId = playlist.getListener().getId();
                    List<Long> songIds = playlist.getSongs()
                            .stream()
                            .map(Song::getId)
                            .toList();

                    playlistDto.setListener(playlist.getListener());
                    playlistDto.setSongs(playlist.getSongs());

                    return playlistDto;

                }).orElseThrow(() -> new EntityNotFoundException
                        ("Playlist with ID " + id + " not found in the database"));
    }

    @Override
    public List<PlaylistResponseDTO> getAllPlaylists() {
        var playlists = playlistRepository.findAll()
                .stream()
                .map(playlist -> {
                    var playlistDto = modelMapper.map(playlist, PlaylistResponseDTO.class);

                    Long listenerId = playlist.getListener().getId();
                    List<Long> songIds = playlist.getSongs()
                            .stream()
                            .map(Song::getId)
                            .toList();

                    playlistDto.setListener(playlist.getListener());
                    playlistDto.setSongs(playlist.getSongs());

                    return playlistDto;

                }).toList();

        if (playlists.isEmpty()) {
            throw new EmptyResultException("No playlists found in the database.");
        } else {
            return playlists;
        }
    }

    @Override
    public PlaylistResponseDTO createPlaylist(PlaylistRequestDTO request) {
        Optional<Listener> listener = listenerRepository.findById(request.getListenerId());
        List<Song> songs = songRepository.findAllById(request.getSongIds());

        if (listener.isEmpty()) {
            throw new EntityNotFoundException("[Creation failed] Listener with ID " + request.getListenerId() +
                    " not found in the database");
        }

        if (songs.isEmpty()) {
            throw new EmptyResultException("[Creation failed] Songs with IDs " + request.getSongIds() +
                    " not found in the database");
        }

        Playlist playlist = modelMapper.map(request, Playlist.class);

        playlist.setListener(listener.get());
        playlist.setSongs(songs);
        playlist.setCreationDate(LocalDate.now());
        playlist.setUpdateDate(LocalDate.now());

        Playlist savedPlaylist = playlistRepository.saveAndFlush(playlist);

        PlaylistResponseDTO response = modelMapper.map(savedPlaylist, PlaylistResponseDTO.class);

        response.setSongs(savedPlaylist.getSongs());

        response.setListener(savedPlaylist.getListener());

        return response;
    }

    @Override
    public Optional<PlaylistResponseDTO> updatePlaylist(Long id, PlaylistRequestDTO request) {

        Optional<Listener> listener = listenerRepository.findById(request.getListenerId());
        List<Song> songs = songRepository.findAllById(request.getSongIds());

        if (songs.isEmpty()) {
            throw new EmptySongsListOnUpdateException("[Update failed] Cannot insert empty song list during update");
        }

        if (listener.isEmpty()) {
            throw new EntityNotFoundException("[Update failed] Listener with ID " + request.getListenerId() +
                    " not found in the database");
        }

        return playlistRepository.findById(id)
                .map(existingPlaylist -> {
                    existingPlaylist.setSongs(songs);
                    existingPlaylist.setUpdateDate(LocalDate.now());

                    try {
                        UniversalFieldUpdater.checkFieldsAndUpdate(request, existingPlaylist);
                    } catch (InvocationTargetException e) {
                        throw new RuntimeException(e);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }

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
