package com.develhope.spring.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "genres")
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idGenre;

    @Column(nullable = false, unique = true)
    private String title;

    public Genre() {
    }

    public Genre(Long idGenre, String title) {
        this.idGenre = idGenre;
        this.title = title;
    }

    public Long getIdGenre() {
        return idGenre;
    }

    public void setIdGenre(Long idGenre) {
        this.idGenre = idGenre;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

