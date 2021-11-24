package com.salesianostriana.dam.realestatesecurity.users.dto;

import com.salesianostriana.dam.realestatesecurity.dto.ViviendaDtoConverter;
import com.salesianostriana.dam.realestatesecurity.users.model.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserDtoConverter {

    private final ViviendaDtoConverter viviendaDtoConverter;

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
                .viviendas(user.getViviendas().stream().map(viviendaDtoConverter::viviendaToGetViviendaDto).collect(Collectors.toList()))
                .build();
    }

}

