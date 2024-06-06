package com.develhope.spring.services.implementations;

import com.develhope.spring.entities.Song;
import com.develhope.spring.repositories.SongRepository;
import com.develhope.spring.services.interfaces.SongService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SongServiceImpl implements SongService {
    @Autowired
    private SongRepository songRepository;
    @Autowired
    private final ModelMapper modelMapper;


    @Override
    public Song createSong(Song song) {
        return songRepository.saveAndFlush(song);
    }

    @Override
    public List<Song> getAllSong() {
        return songRepository.findAll();
    }

    @Override
    public Optional<Song> findSong(long id) {
        return songRepository.findById(id);
    }
    @Override
    public Song updateSong(Long id, Song song) {
        song.setId(id);
        return  songRepository.saveAndFlush(song);
    }
    @Override
    public boolean deleteSong(Long id) {
        if (songRepository.existsById(id)) {
            songRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
