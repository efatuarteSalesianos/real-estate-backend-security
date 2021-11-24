package com.salesianostriana.dam.realestatesecurity.users.controllers;

import com.salesianostriana.dam.realestatesecurity.users.dto.GetPropietarioDto;
import com.salesianostriana.dam.realestatesecurity.users.dto.UserDtoConverter;
import com.salesianostriana.dam.realestatesecurity.users.model.UserEntity;
import com.salesianostriana.dam.realestatesecurity.users.model.UserRoles;
import com.salesianostriana.dam.realestatesecurity.users.services.UserEntityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Log
@RestController
@RequiredArgsConstructor
@RequestMapping("/propietario")
public class PropietarioController {

    private final UserEntityService userEntityService;
    private final UserDtoConverter userDtoConverter;

    @GetMapping("/")
    public ResponseEntity<List<GetPropietarioDto>> getPropietarios() {
        List<GetPropietarioDto> propietarios = userEntityService.findByRole(UserRoles.PROPIETARIO)
                .stream()
                .map(userDtoConverter::convertUserEntityToGetPropietarioDto)
                .collect(Collectors.toList());
        if(propietarios.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity
                .ok().body(propietarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetPropietarioDto> findPropietarioById(@AuthenticationPrincipal UserEntity user, @PathVariable UUID id) {
        if(user.getId().equals(id) || user.getRole().equals(UserRoles.ADMIN))
            return ResponseEntity
                    .of(userEntityService.findById(id)
                            .map(userDtoConverter::convertUserEntityToGetPropietarioDto));
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePropietarioById(@AuthenticationPrincipal UserEntity user, @PathVariable UUID id){
        if(user.getId().equals(id) || user.getRole().equals(UserRoles.ADMIN)) {
            if(userEntityService.findById(id).isEmpty()) {
            return ResponseEntity
                    .notFound()
                    .build();
            }
            else {
                userEntityService.deleteById(id);
                return ResponseEntity
                        .noContent()
                        .build();
            }
        }
        else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }
}
