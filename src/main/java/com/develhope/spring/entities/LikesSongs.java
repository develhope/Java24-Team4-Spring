package com.develhope.spring.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "likes_songs")
public class LikesSongs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_id", nullable = false)
    private Long likeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "song_id", nullable = false)
    private Song song;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "listener_id", nullable = false)
    private Listener listener;

    @Column(name = "is_liked", nullable = false)
    private Boolean isLiked;

    public LikesSongs() {
    }

    public LikesSongs(Song song, Listener listener, Boolean isLiked) {
        this.song = song;
        this.listener = listener;
        this.isLiked = isLiked;
    }

    public Long getLikeId() {
        return likeId;
    }

    public void setLikeId(Long likeId) {
        this.likeId = likeId;
    }

    public Song getSong() {
        return song;
    }

    public void setSong(Song song) {
        this.song = song;
    }

    public Listener getListener() {
        return listener;
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public Boolean getIsLiked() {
        return isLiked;
    }

    public void setIsLiked(Boolean isLiked) {
        this.isLiked = isLiked;
    }
}
