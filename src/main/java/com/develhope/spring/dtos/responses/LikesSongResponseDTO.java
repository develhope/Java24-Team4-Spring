package com.develhope.spring.dtos.responses;

public class LikesSongResponseDTO {

    private Long likeId;
    private Long songId;
    private Long listenerId;
    private Boolean isLiked;

    public LikesSongResponseDTO() {
    }

    public LikesSongResponseDTO(Long likeId, Long songId, Long listenerId, Boolean isLiked) {
        this.likeId = likeId;
        this.songId = songId;
        this.listenerId = listenerId;
        this.isLiked = isLiked;
    }

    public Long getLikeId() {
        return likeId;
    }

    public void setLikeId(Long likeId) {
        this.likeId = likeId;
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