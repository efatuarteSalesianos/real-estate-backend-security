package com.salesianostriana.dam.realestatesecurity.users.dto;

import com.salesianostriana.dam.realestatesecurity.users.model.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserDtoConverter {

    public GetUserDto convertUserEntityToGetUserDto(UserEntity user) {
        return GetUserDto.builder()
                .email(user.getEmail())
                .avatar(user.getAvatar())
                .fullName(user.getFullName())
                .role(user.getRole().name())
                .build();
    }

}

