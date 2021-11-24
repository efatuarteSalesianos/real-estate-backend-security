package com.salesianostriana.dam.realestatesecurity.dto;

import com.salesianostriana.dam.realestatesecurity.model.Tipo;
import lombok.*;

import java.util.UUID;

@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@Builder
public class GetViviendaDto {
    private Long id;
    private String titulo, latlng, direccion, poblacion, provincia, descripcion, avatar;
    private int codigoPostal, numHabitaciones, metrosCuadrados, numBanyos;
    private boolean tienePiscina, tieneAscensor, tieneGaraje;
    private Long inmobiliariaId;
    private UUID propietarioId;
}
