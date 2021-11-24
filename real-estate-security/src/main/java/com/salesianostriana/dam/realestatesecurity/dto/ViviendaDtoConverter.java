package com.salesianostriana.dam.realestatesecurity.dto;

import com.salesianostriana.dam.realestatesecurity.model.Vivienda;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ViviendaDtoConverter {

    public GetViviendaDto viviendaToGetViviendaDto(Vivienda v) {
        return GetViviendaDto
                .builder()
                .id(v.getId())
                .titulo(v.getTitulo())
                .latlng(v.getLatlng())
                .direccion(v.getDireccion())
                .poblacion(v.getPoblacion())
                .provincia(v.getProvincia())
                .descripcion(v.getDescripcion())
                .avatar(v.getAvatar())
                .codigoPostal(v.getCodigoPostal())
                .numHabitaciones(v.getNumHabitaciones())
                .metrosCuadrados(v.getMetrosCuadrados())
                .numBanyos(v.getNumBanyos())
                .tienePiscina(v.isTienePiscina())
                .tieneAscensor(v.isTieneAscensor())
                .tieneGaraje(v.isTieneGaraje())
                .inmobiliariaId(v.getInmobiliaria()==null?null:v.getInmobiliaria().getId())
                .propietarioId(v.getPropietario()==null?null:v.getPropietario().getId())
                .build();
    }

    public Vivienda createViviendaDtoToVivienda(CreateViviendaDto v) {
        return Vivienda
                .builder()
                .titulo(v.getTitulo())
                .descripcion(v.getDescripcion())
                .avatar(v.getAvatar())
                .latlng(v.getLatlng())
                .direccion(v.getDireccion())
                .poblacion(v.getPoblacion())
                .provincia(v.getProvincia())
                .codigoPostal(v.getCodigoPostal())
                .numHabitaciones(v.getNumHabitaciones())
                .metrosCuadrados(v.getMetrosCuadrados())
                .numBanyos(v.getNumBanyos())
                .tipo(v.getTipo())
                .precio(v.getPrecio())
                .tienePiscina(v.isTienePiscina())
                .tieneAscensor(v.isTieneAscensor())
                .tieneGaraje(v.isTieneGaraje())
                .build();
    }

}
