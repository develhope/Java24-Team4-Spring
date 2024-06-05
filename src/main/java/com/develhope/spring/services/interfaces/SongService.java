package com.develhope.spring.services.interfaces;

import com.develhope.spring.entities.Song;

import java.awt.*;
import java.util.List;
import java.util.Optional;

public interface SongService {
    Song createSong(Song song);
    List<Song> getAllSong();
    Optional<Song> findSong(long id);
}
