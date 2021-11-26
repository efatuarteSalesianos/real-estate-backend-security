package com.salesianostriana.dam.realestatesecurity.users.controllers;

import com.salesianostriana.dam.realestatesecurity.model.Inmobiliaria;
import com.salesianostriana.dam.realestatesecurity.users.dto.*;
import com.salesianostriana.dam.realestatesecurity.users.model.UserEntity;
import com.salesianostriana.dam.realestatesecurity.users.model.UserRoles;
import com.salesianostriana.dam.realestatesecurity.users.services.UserEntityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Tag(name="Auth", description = "Clase controladora de la seguridad")
public class UserController {

    private final UserEntityService userEntityService;
    private final UserDtoConverter userDtoConverter;

    @Operation(summary = "Se añade una usuario del rol establecido como parámetro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Se ha creado correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Inmobiliaria.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Hay un error en los datos",
                    content = @Content),
            @ApiResponse(responseCode = "403",
                    description = "Acceso denegado",
                    content = @Content)
    })
    @PostMapping("/auth/register/{role}")
    public ResponseEntity<GetUserDto> newUser(@Parameter(description = "El cuerpo con los atributos del nuevo usuario") @RequestBody CreateUserDto newUser, @Parameter(description = "El rol del nuevo usuario") @PathVariable UserRoles role) {
        UserRoles.valueOf("propietario".toUpperCase());
        UserEntity saved = userEntityService.save(newUser, role);

        if(saved == null)
            return ResponseEntity.badRequest().build();
        else
            return ResponseEntity.status(HttpStatus.CREATED).body(userDtoConverter.convertUserEntityToGetUserDto(saved));
    }

    @Operation(summary = "Se muestra un listado de todos los usuarios con el rol gestor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se muestra correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Inmobiliaria.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No hay gestores",
                    content = @Content),
            @ApiResponse(responseCode = "403",
                    description = "Acceso denegado",
                    content = @Content)
    })
    @GetMapping("/gestores")
    public ResponseEntity<List<GetGestorDto>> getGestores() {
        List<GetGestorDto> gestores = userEntityService.findByRole(UserRoles.GESTOR)
                .stream()
                .map(userDtoConverter::convertUserEntityToGetGestorDto)
                .collect(Collectors.toList());
        if(gestores.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity
                .ok().body(gestores);
    }

    @Operation(summary = "Se muestra un listado de todos los interesados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se muestra correctamente el listado",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserEntity.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No hay interesados",
                    content = @Content),
            @ApiResponse(responseCode = "403",
                    description = "Acceso denegado",
                    content = @Content)
    })
    @GetMapping("/interesado/")
    public ResponseEntity<List<GetPropietarioDto>> listarInteresados() {
        List<GetPropietarioDto> interesados = userEntityService.findInteresados()
                .stream()
                .map(userDtoConverter::convertUserEntityToGetPropietarioDto).collect(Collectors.toList());
        if(interesados.isEmpty())
            return ResponseEntity
                    .notFound()
                    .build();
        return ResponseEntity
                .ok()
                .body(interesados);
    }

//
//    @Operation(summary = "Se muestra la información de un interesado")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200",
//                    description = "Se muestra correctamente la información",
//                    content = {@Content(mediaType = "application/json",
//                            schema = @Schema(implementation = UserEntity.class))}),
//            @ApiResponse(responseCode = "404",
//                    description = "No se encuentra el interesado con el id",
//                    content = @Content),
//            @ApiResponse(responseCode = "403",
//                    description = "Acceso denegado",
//                    content = @Content)
//    })
//    @GetMapping("/interesado/{id}")
//    public ResponseEntity<List<GetPropietarioInteresadoDto>> buscarInteresado(@Parameter(description = "El id del interesado que se busca") @PathVariable UUID interesadoId) {
//
//    }
}
