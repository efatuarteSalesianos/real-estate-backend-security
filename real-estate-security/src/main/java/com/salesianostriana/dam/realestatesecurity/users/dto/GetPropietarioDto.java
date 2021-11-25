package com.salesianostriana.dam.realestatesecurity.users.dto;

import com.salesianostriana.dam.realestatesecurity.dto.GetInteresaDto;
import com.salesianostriana.dam.realestatesecurity.dto.GetViviendaDto;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
@Builder
public class GetPropietarioDto {

    private UUID id;
    private String full_name, email, avatar;
    private int num_viviendas;
    private List<GetViviendaDto> viviendas = new ArrayList<>();
}
