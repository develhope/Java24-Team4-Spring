package com.develhope.spring.dtos.responses;

import com.develhope.spring.entities.User;

public class ListenerResponseDTO {

    private User user;

    public ListenerResponseDTO(User user) {
        this.user = user;
    }

    public ListenerResponseDTO() {
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

}
