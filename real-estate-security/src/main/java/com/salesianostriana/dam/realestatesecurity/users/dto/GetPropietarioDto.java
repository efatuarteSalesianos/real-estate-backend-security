package com.salesianostriana.dam.realestatesecurity.users.dto;

import lombok.*;

@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
@Builder
public class GetPropietarioDto {

    private String full_name, email, avatar;
    private int num_viviendas;

}
