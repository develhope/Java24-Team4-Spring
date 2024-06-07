package com.develhope.spring.dtos.requests;

import java.time.LocalDate;
import java.util.List;

public class PlaylistRequestDTO {
    private String title;
    private Long listenerId;
    private List<Long> songIds;
    private LocalDate creationDate;
    private LocalDate updateDate;

    public PlaylistRequestDTO() {
    }

    public PlaylistRequestDTO(String title, Long listenerId, List<Long> songIds, LocalDate creationDate, LocalDate updateDate) {
        this.title = title;
        this.listenerId = listenerId;
        this.songIds = songIds;
        this.creationDate = creationDate;
        this.updateDate = updateDate;
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

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDate getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
    }
}
