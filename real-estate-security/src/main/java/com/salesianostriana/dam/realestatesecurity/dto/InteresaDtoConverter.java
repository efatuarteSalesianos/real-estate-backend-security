package com.salesianostriana.dam.realestatesecurity.dto;

import com.salesianostriana.dam.realestatesecurity.model.Interesa;
import com.salesianostriana.dam.realestatesecurity.users.dto.GetPropietarioInteresadoDto;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class InteresaDtoConverter {

    public GetInteresaDto interesaToGetInteresaDto(Interesa in){
        return GetInteresaDto
                .builder()
                .interesadoId(in.getInteresado().getId())
                .viviendaId(in.getVivienda().getId())
                .mensaje(in.getMensaje())
                .createdDate(in.getCreatedDate())
                .build();
    }

    public Interesa convertGetPropietarioInteresadoToInteresa(GetPropietarioInteresadoDto interesadoDto) {
        return Interesa
                .builder()
                .createdDate(LocalDateTime.now())
                .mensaje(interesadoDto.getMensaje())
                .build();
    }
}
