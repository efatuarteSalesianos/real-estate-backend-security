package com.salesianostriana.dam.realestatesecurity.users.repositories;

import com.salesianostriana.dam.realestatesecurity.users.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findFirstByEmail(String email);
}
