package com.develhope.spring.dtos.responses;

import com.develhope.spring.entities.UserEntity;

public class ListenerResponseDTO {

    private UserEntity userEntity;

    public ListenerResponseDTO(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public ListenerResponseDTO() {
    }

    public void setUser(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public UserEntity getUser() {
        return userEntity;
    }

}
