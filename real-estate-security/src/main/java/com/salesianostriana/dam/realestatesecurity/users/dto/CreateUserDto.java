package com.salesianostriana.dam.realestatesecurity.users.dto;

import lombok.*;

@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
@Builder
public class CreateUserDto {

    private String email, full_name, avatar, password, password2;

}
