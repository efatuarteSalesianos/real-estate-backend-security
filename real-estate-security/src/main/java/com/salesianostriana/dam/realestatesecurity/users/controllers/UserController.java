package com.salesianostriana.dam.realestatesecurity.users.controllers;

import com.salesianostriana.dam.realestatesecurity.users.dto.CreateUserDto;
import com.salesianostriana.dam.realestatesecurity.users.dto.GetUserDto;
import com.salesianostriana.dam.realestatesecurity.users.dto.UserDtoConverter;
import com.salesianostriana.dam.realestatesecurity.users.model.UserEntity;
import com.salesianostriana.dam.realestatesecurity.users.model.UserRoles;
import com.salesianostriana.dam.realestatesecurity.users.services.UserEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserEntityService userEntityService;
    private final UserDtoConverter userDtoConverter;

    @PostMapping("/auth/register/{role}")
    public ResponseEntity<GetUserDto> newUser(@RequestBody CreateUserDto newUser, @PathVariable UserRoles role) {
        UserRoles.valueOf("propietario".toUpperCase());
        UserEntity saved = userEntityService.save(newUser, role);

        if(saved == null)
            return ResponseEntity.badRequest().build();
        else
            return ResponseEntity.status(HttpStatus.CREATED).body(userDtoConverter.convertUserEntityToGetUserDto(saved));
    }
}
