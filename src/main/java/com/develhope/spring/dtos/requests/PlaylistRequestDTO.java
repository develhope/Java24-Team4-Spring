package com.develhope.spring.dtos.requests;

import java.util.List;

public class PlaylistRequestDTO {
    private String title;
    private Long listenerId;
    private List<Long> songIds;

    public PlaylistRequestDTO() {
    }


    public PlaylistRequestDTO(String title, Long listenerId, List<Long> songIds) {
        this.title = title;
        this.listenerId = listenerId;
        this.songIds = songIds;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getListenerId() {
        return listenerId;
    }

    public void setListenerId(Long listenerId) {
        this.listenerId = listenerId;
    }

    public List<Long> getSongIds() {
        return songIds;
    }

    public void setSongIds(List<Long> songIds) {
        this.songIds = songIds;
    }
}
