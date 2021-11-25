package com.salesianostriana.dam.realestatesecurity.controllers;

import com.salesianostriana.dam.realestatesecurity.dto.CreateInmobiliariaDto;
import com.salesianostriana.dam.realestatesecurity.model.Inmobiliaria;
import com.salesianostriana.dam.realestatesecurity.services.InmobiliariaService;
import com.salesianostriana.dam.realestatesecurity.uploads.PaginationLinkUtils;
import com.salesianostriana.dam.realestatesecurity.users.dto.CreateUserDto;
import com.salesianostriana.dam.realestatesecurity.users.dto.GetUserDto;
import com.salesianostriana.dam.realestatesecurity.users.dto.UserDtoConverter;
import com.salesianostriana.dam.realestatesecurity.users.model.UserEntity;
import com.salesianostriana.dam.realestatesecurity.users.model.UserRoles;
import com.salesianostriana.dam.realestatesecurity.users.services.UserEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/inmobiliaria")
public class InmobiliariaController {

    private final InmobiliariaService service;
    private final UserEntityService userEntityService;
    private final UserDtoConverter userDtoConverter;
    private final PaginationLinkUtils paginationLinkUtils;

    @PostMapping("/")
    public ResponseEntity<CreateInmobiliariaDto> nuevaInmobiliaria(@RequestBody CreateInmobiliariaDto nuevaInmobiliaria) {

        service.save(nuevaInmobiliaria);

        if(nuevaInmobiliaria.getNombre().isEmpty())
            return ResponseEntity.badRequest().build();
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(nuevaInmobiliaria);
    }

    @PostMapping("/{id}/gestor")
    public ResponseEntity<GetUserDto> nuevoGestorEnInmobiliaria(@PathVariable Long id, @RequestBody CreateUserDto nuevoGestor, @AuthenticationPrincipal UserEntity user) {
        UserEntity saved = userEntityService.save(nuevoGestor, UserRoles.GESTOR);
        Optional<Inmobiliaria> i = this.service.findById(id);
        if (i.isEmpty())
            return ResponseEntity
                    .notFound()
                    .build();
        i.get().getGestores().add(saved);
        this.service.save(i.get());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userDtoConverter.convertUserEntityToGetUserDto(saved));
    }

    @DeleteMapping("/gestor/{id}")
    public ResponseEntity<?> eliminarGestorDeInmobiliaria(@PathVariable UUID id, @AuthenticationPrincipal UserEntity user) {
        Optional<UserEntity> gestor = userEntityService.findById(id);
        if(gestor.isEmpty())
            return ResponseEntity
                    .notFound()
                    .build();
        if(user.getInmobiliaria().getId().equals(gestor.get().getInmobiliaria().getId()) || user.getRole().equals(UserRoles.ADMIN)) {
            gestor.get().removeFromInmobiliaria(user.getInmobiliaria());
            userEntityService.deleteById(id);
            this.service.edit(user.getInmobiliaria());
            return ResponseEntity
                    .noContent()
                    .build();
        }
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .build();
    }

}
