package com.develhope.spring.dtos.requests;

public class LikesSongRequestDTO {

    private Long songId;
    private Long listenerId;
    private Boolean isLiked;

    public LikesSongRequestDTO() {
    }

    public LikesSongRequestDTO(Long songId, Long listenerId, Boolean isLiked) {
        this.songId = songId;
        this.listenerId = listenerId;
        this.isLiked = isLiked;
    }

    public Long getSongId() {
        return songId;
    }

    public void setSongId(Long songId) {
        this.songId = songId;
    }

    public Long getListenerId() {
        return listenerId;
    }

    public void setListenerId(Long listenerId) {
        this.listenerId = listenerId;
    }

    public Boolean getIsLiked() {
        return isLiked;
    }

    public void setIsLiked(Boolean isLiked) {
        this.isLiked = isLiked;
    }
}
