package com.salesianostriana.dam.realestatesecurity.controllers;

import com.salesianostriana.dam.realestatesecurity.dto.CreateInmobiliariaDto;
import com.salesianostriana.dam.realestatesecurity.dto.GetInmobiliariaDto;
import com.salesianostriana.dam.realestatesecurity.dto.InmobiliariaDtoConverter;
import com.salesianostriana.dam.realestatesecurity.model.Inmobiliaria;
import com.salesianostriana.dam.realestatesecurity.model.Vivienda;
import com.salesianostriana.dam.realestatesecurity.services.InmobiliariaService;
import com.salesianostriana.dam.realestatesecurity.services.ViviendaService;
import com.salesianostriana.dam.realestatesecurity.uploads.PaginationLinkUtils;
import com.salesianostriana.dam.realestatesecurity.users.dto.CreateUserDto;
import com.salesianostriana.dam.realestatesecurity.users.dto.GetPropietarioDto;
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

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/inmobiliaria")
public class InmobiliariaController {

    private final InmobiliariaService service;
    private final UserEntityService userEntityService;
    private final UserDtoConverter userDtoConverter;
    private final InmobiliariaDtoConverter dtoConverter;
    private final ViviendaService viviendaService;

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
        if((user.getRole().equals(UserRoles.GESTOR) && user.getInmobiliaria().getId().equals(id)) || user.getRole().equals(UserRoles.ADMIN)) {
            Optional<Inmobiliaria> inmo = service.findById(id);
            if(inmo.isEmpty())
                return ResponseEntity
                        .notFound()
                        .build();
            UserEntity saved = userEntityService.save(nuevoGestor, UserRoles.GESTOR);
            if(saved == null)
                return ResponseEntity
                        .badRequest()
                        .build();
            else
                inmo.get().getGestores().add(saved);
                service.edit(inmo.get());
                saved.addToInmobiliaria(inmo.get());
                userEntityService.edit(saved);
                return ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body(userDtoConverter.convertUserEntityToGetUserDto(saved));
        }
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .build();
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

    @GetMapping("/gestores")
    public ResponseEntity<List<GetUserDto>> mostrarGestoresDeInmobiliaria(@PathVariable Long id, @AuthenticationPrincipal UserEntity user) {
        if(user.getInmobiliaria().getId().equals(id)) {
            List<GetUserDto> gestores = userEntityService.findGestoresDeInmobiliaria(id)
                    .stream()
                    .map(userDtoConverter::convertUserEntityToGetUserDto)
                    .collect(Collectors.toList());
            if (gestores.isEmpty())
                return ResponseEntity
                        .notFound()
                        .build();
            return ResponseEntity
                    .ok()
                    .body(gestores);
        }
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .build();
    }

    @GetMapping("")
    public ResponseEntity<List<GetInmobiliariaDto>> listarInmobiliarias() {
        List <GetInmobiliariaDto> inmobiliarias = service.findAll()
                .stream()
                .map(dtoConverter::inmobiliariaToGetInmobiliariaDto)
                .collect(Collectors.toList());
        if(inmobiliarias.isEmpty())
            return ResponseEntity
                    .notFound()
                    .build();
        return ResponseEntity
                .ok()
                .body(inmobiliarias);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetInmobiliariaDto> buscarInmobiliaria(@PathVariable Long id) {
        return ResponseEntity
                .of(this.service.findById(id)
                .map(dtoConverter::inmobiliariaToGetInmobiliariaDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteInmobiliaria(@PathVariable Long id) {

        Optional<Inmobiliaria> inmo = service.findById(id);

        if(inmo.isEmpty())
            return ResponseEntity
                    .notFound()
                    .build();

        List<Vivienda> viviendas = viviendaService.findViviendasDeInmobiliaria(id);

        for (Vivienda v: viviendas) {
            v.removeFromInmobiliaria(inmo.get());
            viviendaService.edit(v);
        }
        return ResponseEntity
                .noContent()
                .build();
    }

}
