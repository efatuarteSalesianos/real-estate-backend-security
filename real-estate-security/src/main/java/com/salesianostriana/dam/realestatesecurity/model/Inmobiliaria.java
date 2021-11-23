package com.salesianostriana.dam.realestatesecurity.model;

import com.salesianostriana.dam.realestatesecurity.users.model.UserEntity;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@Builder
public class Inmobiliaria implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre, email, telefono, avatar;

    @Builder.Default
    @OneToMany(mappedBy = "inmobiliaria", orphanRemoval = true)
    private List<Vivienda> viviendas = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "inmobiliaria", orphanRemoval = true)
    private List<UserEntity> gestores = new ArrayList<>();
}
