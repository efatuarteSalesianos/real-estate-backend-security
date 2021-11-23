package com.salesianostriana.dam.realestatesecurity.users.dto;

import lombok.*;

import java.util.UUID;

@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
@Builder
public class GetPropietarioDto {

    private UUID id;
    private String full_name, email, avatar;
    private int num_viviendas;

}
