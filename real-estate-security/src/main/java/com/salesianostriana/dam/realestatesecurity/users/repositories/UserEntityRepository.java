package com.salesianostriana.dam.realestatesecurity.users.repositories;

import com.salesianostriana.dam.realestatesecurity.users.model.UserEntity;
import com.salesianostriana.dam.realestatesecurity.users.model.UserRoles;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserEntityRepository extends JpaRepository<UserEntity, UUID> {

    Optional<UserEntity> findFirstByEmail(String email);

    @EntityGraph(value = "grafo-propietario-con-viviendas", type = EntityGraph.EntityGraphType.FETCH)
    List<UserEntity> findUserEntityByRole(UserRoles role);

    @Query("select u from UserEntity u left join u.inmobiliaria i where i.id = :inmobiliariaId")
    @EntityGraph(value = "grafo-gestor-con-inmobiliaria", type = EntityGraph.EntityGraphType.FETCH)
    List<UserEntity> findByInmobiliariaIdUsingQuery(@Param("inmobiliariaId") Long inmobiliariaId);

    @Query("select u from UserEntity u left join u.intereses i where u.intereses>0")
    List<UserEntity> findInteresados();
}
