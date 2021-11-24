package com.salesianostriana.dam.realestatesecurity.services;

import com.salesianostriana.dam.realestatesecurity.dto.CreateViviendaDto;
import com.salesianostriana.dam.realestatesecurity.dto.ViviendaDtoConverter;
import com.salesianostriana.dam.realestatesecurity.model.Tipo;
import com.salesianostriana.dam.realestatesecurity.model.Vivienda;
import com.salesianostriana.dam.realestatesecurity.repositories.ViviendaRepository;
import com.salesianostriana.dam.realestatesecurity.services.base.BaseService;
import com.salesianostriana.dam.realestatesecurity.users.model.UserEntity;
import com.salesianostriana.dam.realestatesecurity.users.services.UserEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ViviendaService extends BaseService<Vivienda, Long, ViviendaRepository> {

    private final UserEntityService userEntityService;
    private final ViviendaDtoConverter dtoConverter;

    public Vivienda save(@RequestBody CreateViviendaDto viviendaNueva, @AuthenticationPrincipal UserEntity user) {

        Vivienda v = dtoConverter.createViviendaDtoToVivienda(viviendaNueva);
        UserEntity p = userEntityService.findById(user.getId()).orElse(null);
        v.setPropietario(p);

        return v;
    }

    public Page<Vivienda> findByArgs (final Optional<String> titulo,
                                      final Optional<String> provincia,
                                      final Optional<String> direccion,
                                      final Optional<String> poblacion,
                                      final Optional<Integer>codigoPostal,
                                      final Optional<Integer>numBanyos,
                                      final Optional<Integer>numHabitaciones,
                                      final Optional<Integer>metrosCuadrados,
                                      final Optional<Double>precio,
                                      final Optional <Boolean>tienePiscina,
                                      final Optional<Boolean> tieneAscensor,
                                      final Optional<Boolean> tieneGaraje,
                                      final Optional<Tipo> tipo,
                                      Pageable pageable){

        Specification<Vivienda> specTituloVivienda = new Specification<Vivienda>() {
            @Override
            public Predicate toPredicate(Root<Vivienda> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if(titulo.isPresent()){
                    //Si siempre está presente se filtra por nombre
                    return criteriaBuilder.like(criteriaBuilder.lower(root.get("titulo")), "%" + titulo.get() + "%");
                }else{
                    //Si no está presente siempre estará en verdadero
                    return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
                }
            }
        };
        Specification<Vivienda> specProvinciaVivienda = new Specification<Vivienda>() {
            @Override
            public Predicate toPredicate(Root<Vivienda> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if(provincia.isPresent()){
                    //Si siempre está presente se filtra por nombre
                    return criteriaBuilder.like(criteriaBuilder.lower(root.get("provincia")), "%" + provincia.get() + "%");
                }else{
                    //Si no está presente siempre estará en verdadero
                    return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
                }
            }
        };
        Specification<Vivienda> specDireccionVivienda = new Specification<Vivienda>() {
            @Override
            public Predicate toPredicate(Root<Vivienda> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if(direccion.isPresent()){
                    //Si siempre está presente se filtra por nombre
                    return criteriaBuilder.like(criteriaBuilder.lower(root.get("direccion")), "%" + direccion.get() + "%");
                }else{
                    //Si no está presente siempre estará en verdadero
                    return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
                }
            }
        };
        Specification<Vivienda> specPoblacionVivienda = new Specification<Vivienda>() {
            @Override
            public Predicate toPredicate(Root<Vivienda> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if(poblacion.isPresent()){
                    //Si siempre está presente se filtra por nombre
                    return criteriaBuilder.like(criteriaBuilder.lower(root.get("poblacion")), "%" + poblacion.get() + "%");
                }else{
                    //Si no está presente siempre estará en verdadero
                    return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
                }
            }
        };
        Specification<Vivienda> specCodigoPostalVivienda = new Specification<Vivienda>() {
            @Override
            public Predicate toPredicate(Root<Vivienda> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if(codigoPostal.isPresent()){
                    //Si siempre está presente se filtra por nombre
                    return criteriaBuilder.equal(root.get("codigoPostal"),  codigoPostal.get() );
                }else{
                    //Si no está presente siempre estará en verdadero
                    return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
                }
            }
        };
        Specification<Vivienda> specBanyosVivienda = new Specification<Vivienda>() {
            @Override
            public Predicate toPredicate(Root<Vivienda> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if(numBanyos.isPresent()){
                    //Si siempre está presente se filtra por nombre
                    return criteriaBuilder.equal(root.get("numBanyos"),  numBanyos.get() );
                }else{
                    //Si no está presente siempre estará en verdadero
                    return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
                }
            }
        };
        Specification<Vivienda> specNumHabitacionesVivienda = new Specification<Vivienda>() {
            @Override
            public Predicate toPredicate(Root<Vivienda> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if(numHabitaciones.isPresent()){
                    //Si siempre está presente se filtra por nombre
                    return criteriaBuilder.equal(root.get("numHabitaciones"),  numHabitaciones.get() );
                }else{
                    //Si no está presente siempre estará en verdadero
                    return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
                }
            }
        };
        Specification<Vivienda> specMetrosCuadradosVivienda = new Specification<Vivienda>() {
            @Override
            public Predicate toPredicate(Root<Vivienda> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if(metrosCuadrados.isPresent()){
                    //Si siempre está presente se filtra por nombre
                    return criteriaBuilder.equal(root.get("metrosCuadrados"),  metrosCuadrados.get() );
                }else{
                    //Si no está presente siempre estará en verdadero
                    return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
                }
            }
        };
        Specification<Vivienda> precioMenorQue = new Specification<Vivienda>() {
            @Override
            public Predicate toPredicate(Root<Vivienda> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if(precio.isPresent()){
                    //Si siempre está presente se filtra por nombre
                    return criteriaBuilder.lessThanOrEqualTo(root.get("precio"), precio.get());
                }else{
                    //Si no está presente siempre estará en verdadero
                    return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
                }
            }
        };
        Specification<Vivienda> specTienePiscina = new Specification<Vivienda>() {
            @Override
            public Predicate toPredicate(Root<Vivienda> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if(tienePiscina.isPresent()){
                    //Si siempre está presente se filtra por nombre
                    return criteriaBuilder.equal(root.get("tienePiscina"), tienePiscina.get());
                }else{
                    //Si no está presente siempre estará en verdadero
                    return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
                }
            }
        };
        Specification<Vivienda> specAscensor = new Specification<Vivienda>() {
            @Override
            public Predicate toPredicate(Root<Vivienda> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if(tieneAscensor.isPresent()){
                    //Si siempre está presente se filtra por nombre
                    return criteriaBuilder.equal(root.get("tieneAscensor"), tieneAscensor.get());
                }else{
                    //Si no está presente siempre estará en verdadero
                    return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
                }
            }
        };
        Specification<Vivienda> specGaraje = new Specification<Vivienda>() {
            @Override
            public Predicate toPredicate(Root<Vivienda> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if(tieneGaraje.isPresent()){
                    //Si siempre está presente se filtra por nombre
                    return criteriaBuilder.equal(root.get("tieneGaraje"), tieneGaraje.get());
                }else{
                    //Si no está presente siempre estará en verdadero
                    return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
                }
            }
        };
        //Funciona usando mayúsculas
        Specification<Vivienda> specTipo = new Specification<Vivienda>() {
            @Override
            public Predicate toPredicate(Root<Vivienda> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if(tipo.isPresent()){
                    //Si siempre está presente se filtra por nombre
                    return criteriaBuilder.equal(root.get("tipo"), tipo.get());
                }else{
                    //Si no está presente siempre estará en verdadero
                    return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
                }
            }
        };
        Specification<Vivienda> todas = specTituloVivienda
                .and(specProvinciaVivienda)
                .and(specDireccionVivienda)
                .and(specPoblacionVivienda)
                .and(specCodigoPostalVivienda)
                .and(specBanyosVivienda)
                .and(specNumHabitacionesVivienda)
                .and(specMetrosCuadradosVivienda)
                .and(precioMenorQue)
                .and(specTienePiscina)
                .and(specAscensor)
                .and(specGaraje)
                .and(specTipo);

        return this.repositorio.findAll(todas, pageable);
    }

    public List<Vivienda> topNViviendas(int limit) {
        return repositorio.topViviendas(limit);
    }

}
