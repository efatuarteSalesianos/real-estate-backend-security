package com.salesianostriana.dam.realestatesecurity.security.dto;

import com.salesianostriana.dam.realestatesecurity.users.model.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class JwtUserConverter {

    public JwtUserResponse convertUserToJwtUserResponse(UserEntity user, String jwt) {
        return JwtUserResponse.builder()
                .id(user.getId())
                .fullName(user.getFull_name())
                .email(user.getEmail())
                .avatar(user.getAvatar())
                .role(user.getRole().name())
                .token(jwt)
                .build();
    }

}
