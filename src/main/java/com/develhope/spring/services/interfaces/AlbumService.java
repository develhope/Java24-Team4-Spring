package com.develhope.spring.services.interfaces;

import com.develhope.spring.entities.Album;

import java.util.List;
import java.util.Optional;

public interface AlbumService {
    Album createAlbum(Album album);

    List<Album> getAllAlbums();

    Optional<Album> albumById(Long id);

    Album updateAlbum(Long id, Album album);

    public void albumDelete(Long id);


}
