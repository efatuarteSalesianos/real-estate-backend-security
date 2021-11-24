package com.salesianostriana.dam.realestatesecurity.dto;

import com.salesianostriana.dam.realestatesecurity.model.Tipo;
import lombok.*;

@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@Builder
public class EditViviendaDto {

    private String titulo, latlng, direccion, poblacion, provincia, descripcion, avatar;
    private double precio;
    private int codigoPostal, numHabitaciones, metrosCuadrados, numBanyos;
    private boolean tienePiscina, tieneAscensor, tieneGaraje;
    private Tipo tipo;

}
