package com.develhope.spring.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "listeners")
public class Listener {

    @Id
    private Long id;

    @MapsId
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "listener", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Playlist> playlists;

    @JsonIgnore
    @OneToOne(fetch = FetchType.EAGER, mappedBy = "listener", cascade = CascadeType.ALL)
    private Subscription subscription;

    public Listener(Long id, User user) {
        this.id = id;
        this.user = user;
    }

    public Listener() {
    }

    public Subscription getSubscription() {
        return subscription;
    }

    public void setSubscription(Subscription subscription) {
        this.subscription = subscription;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Playlist> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(List<Playlist> playlists) {
        this.playlists = playlists;
    }

    public void addPlaylist(Playlist playlist) {
        this.playlists.add(playlist);
        playlist.setListener(this);
    }

    public void removePlaylist(Playlist playlist) {
        this.playlists.remove(playlist);
        playlist.setListener(null);
    }
}
