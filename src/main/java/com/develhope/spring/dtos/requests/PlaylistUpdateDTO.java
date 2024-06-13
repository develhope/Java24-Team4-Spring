package com.develhope.spring.dtos.requests;

import java.util.List;

public class PlaylistUpdateDTO {

    private String title;
    private List<Long> songIds;

    public PlaylistUpdateDTO() {
    }

    public PlaylistUpdateDTO(String title, List<Long> songIds) {
        this.title = title;
        this.songIds = songIds;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Long> getSongIds() {
        return songIds;
    }

    public void setSongIds(List<Long> songIds) {
        this.songIds = songIds;
    }
}
