package com.salesianostriana.dam.realestatesecurity.users.dto;

import lombok.*;

@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
@Builder
public class GetUserDto {

    private String email, avatar, full_name, role;

}
