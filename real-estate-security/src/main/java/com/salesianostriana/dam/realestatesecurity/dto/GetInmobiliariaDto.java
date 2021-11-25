package com.salesianostriana.dam.realestatesecurity.dto;

import com.salesianostriana.dam.realestatesecurity.users.dto.GetUserDto;
import lombok.*;

import java.util.List;

@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@Builder
public class GetInmobiliariaDto {
    private Long id;
    private String nombre, email, telefono, avatar;
    private List<GetViviendaDto> viviendas;
    private List<GetUserDto> gestores;
}
