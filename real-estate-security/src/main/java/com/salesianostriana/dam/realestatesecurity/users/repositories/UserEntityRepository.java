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

    List<UserEntity> findUserEntityByRole(UserRoles role);

//    @Query("select u from UserEntity u left join u.inmobiliaria i where i.id = :inmobiliariaId")
//    @EntityGraph(value = "gestor-con-inmobiliarias", type = EntityGraph.EntityGraphType.FETCH)
//    List<UserEntity> findByInmobiliariaIdUsingQuery(@Param("inmobiliariaId") Long inmobiliariaId);
}
