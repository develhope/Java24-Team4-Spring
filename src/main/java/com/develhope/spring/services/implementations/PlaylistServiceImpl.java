package com.develhope.spring.services.implementations;

import com.develhope.spring.dtos.requests.PlaylistRequestDTO;
import com.develhope.spring.dtos.responses.PlaylistResponseDTO;
import com.develhope.spring.entities.Listener;
import com.develhope.spring.entities.Playlist;
import com.develhope.spring.entities.Song;
import com.develhope.spring.exceptions.EmptyResultException;
import com.develhope.spring.exceptions.EmptySongsListOnUpdateException;
import com.develhope.spring.exceptions.NegativeIdException;
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
                .map(playlist -> modelMapper.map(playlist, PlaylistResponseDTO.class)

                ).orElseThrow(() -> new EntityNotFoundException
                        ("Playlist with ID " + id + " not found in the database"));
    }

    @Override
    public List<PlaylistResponseDTO> getAllPlaylists() {
        var playlists = playlistRepository.findAll()
                .stream()
                .map(playlist -> modelMapper.map(playlist, PlaylistResponseDTO.class)

                ).toList();

        if (playlists.isEmpty()) {
            throw new EmptyResultException("No playlists found in the database.");
        } else {
            return playlists;
        }
    }

    @Override
    public PlaylistResponseDTO createPlaylist(PlaylistRequestDTO request) {

        if (request.getListenerId() < 0) {
            throw new NegativeIdException(
                    "[Creation failed] Listener ID cannot be negative. Now: " + request.getListenerId());
        }

        request.getSongIds().forEach(SongId -> {
            if (SongId < 0) {
                throw new NegativeIdException(
                        "[Creation failed] One or more of Song IDs is negative. IDs Now: " + request.getSongIds()
                );
            }
        });

        Optional<Listener> listener = listenerRepository.findById(request.getListenerId());
        List<Song> songs = songRepository.findAllById(request.getSongIds());

        if (listener.isEmpty()) {
            throw new EntityNotFoundException(
                    "[Creation failed] Listener with ID " + request.getListenerId() + " not found in the database"
            );
        }

        if (songs.isEmpty()) {
            throw new EmptyResultException(
                    "[Creation failed] Songs with IDs " + request.getSongIds() + " not found in the database"
            );
        }

        Playlist playlist = modelMapper.map(request, Playlist.class);

        playlist.setListener(listener.get());
        playlist.setSongs(songs);
        playlist.setCreationDate(LocalDate.now());
        playlist.setUpdateDate(LocalDate.now());

        var savedPlaylist = playlistRepository.saveAndFlush(playlist);
        var responseDTO = modelMapper.map(savedPlaylist, PlaylistResponseDTO.class);

        responseDTO.setSongs(savedPlaylist.getSongs());
        responseDTO.setListener(savedPlaylist.getListener());

        return responseDTO;
    }

    @Override
    public Optional<PlaylistResponseDTO> updatePlaylist(Long id, PlaylistRequestDTO request) {
        if (id < 0) {
            throw new NegativeIdException(
                    "[Update failed] Playlist ID cannot be negative. Now: " + id);
        }
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

                    var updatedPlaylist = playlistRepository.saveAndFlush(existingPlaylist);

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
