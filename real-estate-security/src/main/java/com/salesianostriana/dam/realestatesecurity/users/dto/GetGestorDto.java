package com.salesianostriana.dam.realestatesecurity.users.dto;

import com.salesianostriana.dam.realestatesecurity.dto.GetViviendaDto;
import com.salesianostriana.dam.realestatesecurity.model.Inmobiliaria;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
@Builder
public class GetGestorDto {

    private UUID id;
    private String full_name, email, avatar;
    private Long inmobiliariaId;
}
