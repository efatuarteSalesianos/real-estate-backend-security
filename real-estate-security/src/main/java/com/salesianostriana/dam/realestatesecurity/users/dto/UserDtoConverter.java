package com.salesianostriana.dam.realestatesecurity.users.dto;

import com.salesianostriana.dam.realestatesecurity.users.model.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserDtoConverter {

    public GetUserDto convertUserEntityToGetUserDto(UserEntity user) {
        return GetUserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .avatar(user.getAvatar())
                .full_name(user.getFull_name())
                .role(user.getRole().name())
                .build();
    }

    public GetPropietarioDto convertUserEntityToGetPropietarioDto(UserEntity user) {
        return GetPropietarioDto.builder()
                .id(user.getId())
                .full_name(user.getFull_name())
                .email(user.getEmail())
                .avatar(user.getAvatar())
                .num_viviendas(user.getViviendas().size())
                .build();
    }

}

