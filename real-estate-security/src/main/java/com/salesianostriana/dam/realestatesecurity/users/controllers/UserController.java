package com.salesianostriana.dam.realestatesecurity.users.controllers;

import com.salesianostriana.dam.realestatesecurity.users.dto.CreateUserDto;
import com.salesianostriana.dam.realestatesecurity.users.dto.GetPropietarioDto;
import com.salesianostriana.dam.realestatesecurity.users.dto.GetUserDto;
import com.salesianostriana.dam.realestatesecurity.users.dto.UserDtoConverter;
import com.salesianostriana.dam.realestatesecurity.users.model.UserEntity;
import com.salesianostriana.dam.realestatesecurity.users.model.UserRoles;
import com.salesianostriana.dam.realestatesecurity.users.services.UserEntityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Log
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
            return ResponseEntity.ok(userDtoConverter.convertUserEntityToGetUserDto(saved));
    }

    @GetMapping("/propietario/")
    public ResponseEntity<List<GetPropietarioDto>> getPropietarios() {
        List<GetPropietarioDto> propietarios = userEntityService.findAll()
                .stream()
                .filter(u -> u.getRole().equals(UserRoles.PROPIETARIO))
                .map(userDtoConverter::convertUserEntityToGetPropietarioDto)
                .collect(Collectors.toList());
        if(propietarios.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity
                .ok().body(propietarios);
    }

//    @GetMapping("/propietario/{id}")
//    public ResponseEntity<GetUserDto> findPropietario(@PathVariable UUID id) {
//
//    }
}
