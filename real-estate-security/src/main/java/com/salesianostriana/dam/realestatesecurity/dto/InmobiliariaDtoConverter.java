package com.salesianostriana.dam.realestatesecurity.dto;

import com.salesianostriana.dam.realestatesecurity.model.Inmobiliaria;
import org.springframework.stereotype.Component;

@Component
public class InmobiliariaDtoConverter {

    public Inmobiliaria createInmobiliariaDtoToInmobiliaria(CreateInmobiliariaDto inmobiliaria) {
        return Inmobiliaria
                .builder()
                .nombre(inmobiliaria.getNombre())
                .email(inmobiliaria.getEmail())
                .telefono(inmobiliaria.getTelefono())
                .avatar(inmobiliaria.getAvatar())
                .build();
    }
}
