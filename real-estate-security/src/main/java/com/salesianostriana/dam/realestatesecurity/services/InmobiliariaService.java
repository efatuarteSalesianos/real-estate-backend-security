package com.salesianostriana.dam.realestatesecurity.services;

import com.salesianostriana.dam.realestatesecurity.dto.CreateInmobiliariaDto;
import com.salesianostriana.dam.realestatesecurity.dto.CreateViviendaDto;
import com.salesianostriana.dam.realestatesecurity.dto.InmobiliariaDtoConverter;
import com.salesianostriana.dam.realestatesecurity.model.Inmobiliaria;
import com.salesianostriana.dam.realestatesecurity.model.Vivienda;
import com.salesianostriana.dam.realestatesecurity.repositories.InmobiliariaRepository;
import com.salesianostriana.dam.realestatesecurity.services.base.BaseService;
import com.salesianostriana.dam.realestatesecurity.users.model.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InmobiliariaService extends BaseService<Inmobiliaria, Long, InmobiliariaRepository> {

    private final InmobiliariaDtoConverter dtoConverter;

    public Inmobiliaria save(CreateInmobiliariaDto nuevaInmobiliaria) {
        Inmobiliaria i = dtoConverter.createInmobiliariaDtoToInmobiliaria(nuevaInmobiliaria);
        this.save(i);
        return i;
    }
}
