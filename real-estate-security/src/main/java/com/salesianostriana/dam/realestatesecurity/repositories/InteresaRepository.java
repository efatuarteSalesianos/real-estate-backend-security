package com.salesianostriana.dam.realestatesecurity.repositories;

import com.salesianostriana.dam.realestatesecurity.model.Interesa;
import com.salesianostriana.dam.realestatesecurity.model.InteresaPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface InteresaRepository extends JpaRepository<Interesa, InteresaPK> {

    @Query(value = """
            SELECT i
            FROM Interesa i
            WHERE i.vivienda.id = :id
              AND  i.interesado.id = :id2
            """, nativeQuery = true)
    Interesa findOne(@Param("id") Long idVivienda, @Param("id2") UUID idInteresado);
}
