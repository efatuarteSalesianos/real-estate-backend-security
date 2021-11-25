package com.salesianostriana.dam.realestatesecurity.services;

import com.salesianostriana.dam.realestatesecurity.model.Interesa;
import com.salesianostriana.dam.realestatesecurity.model.InteresaPK;
import com.salesianostriana.dam.realestatesecurity.model.Vivienda;
import com.salesianostriana.dam.realestatesecurity.repositories.InteresaRepository;
import com.salesianostriana.dam.realestatesecurity.services.base.BaseService;
import com.salesianostriana.dam.realestatesecurity.users.model.UserEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class InteresaService extends BaseService<Interesa, InteresaPK, InteresaRepository> {

    public Interesa createInteresa(Vivienda v, UserEntity user, String mensaje) {
        Interesa interesa = Interesa
                .builder()
                .interesado(user)
                .vivienda(v)
                .mensaje(mensaje)
                .build();
        interesa.addToInteresado(user);
        interesa.addToVivienda(v);
        this.save(interesa);
        return interesa;
    }

    public Interesa findInteresa (Long id1, UUID id2) {
        return repositorio.findOne(id1, id2);
    }
}
