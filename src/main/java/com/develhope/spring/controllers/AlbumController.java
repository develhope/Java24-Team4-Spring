package com.develhope.spring.controllers;


import com.develhope.spring.entities.Album;
import com.develhope.spring.services.interfaces.AlbumService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/album")
public class AlbumController {

    @Autowired
    private AlbumService albumService;
    @PostMapping("/")
    public Album albumCreate(Album album){
        return albumService.createAlbum(album);
    }
    @GetMapping("/")
    public List<Album> getAllAlbums(){
        return albumService.getAllAlbums();
    }
    @GetMapping("/{id}")
    public Optional<Album> albumById(Long id){
        return albumService.albumById(id);
    }
    @PutMapping("/{id}")
    public Album updateAlbum(Long id, Album album){
        return albumService.updateAlbum(id, album);
    }
    @DeleteMapping("/{id}")
    public void albumDelete(Long id){
        albumService.albumDelete(id);
    }

}