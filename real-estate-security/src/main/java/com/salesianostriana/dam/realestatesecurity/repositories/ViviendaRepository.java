package com.salesianostriana.dam.realestatesecurity.repositories;

import com.salesianostriana.dam.realestatesecurity.model.Vivienda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ViviendaRepository extends JpaRepository<Vivienda, Long>, JpaSpecificationExecutor<Vivienda> {

    @Query(value = """
            select * from vivienda v
            where v.id in
            (select v1.id
            from vivienda v1 join interesa i
            on v1.id = i.vivienda_id
            group by v1.id
            order by count(*) desc
            limit = :limit)
            """, nativeQuery = true)
    public List<Vivienda> topViviendas(@Param("limit") int limit);

    @Query(value = """
            select * from vivienda v
            where v.id in
            (select * from inmobiliaria i
            where i.id = :id)
            """, nativeQuery = true)
    public List<Vivienda> viviendasDeInmobiliaria(@Param("id") Long id);



}
