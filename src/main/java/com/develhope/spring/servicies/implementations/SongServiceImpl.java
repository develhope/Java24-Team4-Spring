package com.develhope.spring.servicies.implementations;

import com.develhope.spring.entities.Song;
import com.develhope.spring.repositories.SongRepository;
import com.develhope.spring.servicies.interfaces.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SongServiceImpl implements SongService {
    @Autowired
    private SongRepository songRepository;


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
}
