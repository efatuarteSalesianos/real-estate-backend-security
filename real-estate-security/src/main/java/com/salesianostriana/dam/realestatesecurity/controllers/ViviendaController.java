package com.salesianostriana.dam.realestatesecurity.controllers;

import com.salesianostriana.dam.realestatesecurity.dto.CreateViviendaDto;
import com.salesianostriana.dam.realestatesecurity.dto.GetViviendaDto;
import com.salesianostriana.dam.realestatesecurity.dto.ViviendaDtoConverter;
import com.salesianostriana.dam.realestatesecurity.model.Tipo;
import com.salesianostriana.dam.realestatesecurity.model.Vivienda;
import com.salesianostriana.dam.realestatesecurity.services.ViviendaService;
import com.salesianostriana.dam.realestatesecurity.uploads.PaginationLinkUtils;
import com.salesianostriana.dam.realestatesecurity.users.model.UserEntity;
import com.salesianostriana.dam.realestatesecurity.users.services.UserEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/vivienda")
public class ViviendaController {

    private final ViviendaService service;
    private final ViviendaDtoConverter dtoConverter;
    private final UserEntityService userEntityService;
    private final PaginationLinkUtils paginationLinkUtils;

    @PostMapping("/")
    public ResponseEntity<CreateViviendaDto> nuevaInmobiliaria(@RequestBody CreateViviendaDto viviendaNueva, @AuthenticationPrincipal UserEntity user) {
        service.save(viviendaNueva, user);
        return ResponseEntity.
                status(HttpStatus.CREATED)
                .body(viviendaNueva);
    }

    @GetMapping("/")
    public ResponseEntity<Page<GetViviendaDto>> buscarViviendasConFiltros(@RequestParam("titulo") Optional<String> titulo,
                                                                          @RequestParam("provincia") Optional<String> provincia,
                                                                          @RequestParam("direccion") Optional<String> direccion,
                                                                          @RequestParam("poblacion") Optional<String> poblacion,
                                                                          @RequestParam("codigoPostal") Optional<Integer> codigoPostal,
                                                                          @RequestParam("numBanyos") Optional<Integer> numBanyos,
                                                                          @RequestParam("numHabitaciones") Optional<Integer> numHabitaciones,
                                                                          @RequestParam("metrosCuadrados") Optional<Integer> metrosCuadrados,
                                                                          @RequestParam("precio") Optional<Double> precio,
                                                                          @RequestParam("tienePiscina") Optional<Boolean> tienePiscina,
                                                                          @RequestParam("tieneAscensor") Optional<Boolean> tieneAscensor,
                                                                          @RequestParam("tieneGaraje") Optional<Boolean> tieneGaraje,
                                                                          @RequestParam("tipo") Optional<Tipo> tipo,
                                                                          Pageable pageable, HttpServletRequest request) {

        Page<Vivienda> resultado = service.findByArgs(titulo, provincia, direccion, poblacion, codigoPostal, numBanyos, numHabitaciones, metrosCuadrados, precio, tienePiscina, tieneAscensor, tieneGaraje, tipo, pageable);
        if (resultado.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            Page<GetViviendaDto> dtoList = resultado.map(dtoConverter::viviendaToGetViviendaDto);
            UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(request.getRequestURL().toString());
            return ResponseEntity.ok().header("link", paginationLinkUtils.createLinkHeader(dtoList, uriBuilder)).body(dtoList);
        }
    }

    @GetMapping("/top/?n={limit}")
    public ResponseEntity<List<GetViviendaDto>> topNViviendas(@PathVariable int limit) {
        List<GetViviendaDto> topViviendas = service.topNViviendas(limit)
                .stream()
                .map(dtoConverter::viviendaToGetViviendaDto)
                .collect(Collectors.toList());
        if(topViviendas.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity
                .ok().body(topViviendas);
    }

}
