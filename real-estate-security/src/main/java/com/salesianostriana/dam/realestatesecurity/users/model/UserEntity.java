package com.salesianostriana.dam.realestatesecurity.users.model;

import com.salesianostriana.dam.realestatesecurity.model.Inmobiliaria;
import com.salesianostriana.dam.realestatesecurity.model.Interesa;
import com.salesianostriana.dam.realestatesecurity.model.Vivienda;
import org.hibernate.annotations.Parameter;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NaturalId;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
@Builder
public class UserEntity implements UserDetails, Serializable {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator",
            parameters = {
                    @Parameter(
                            name = "uuid_gen_strategy_class",
                            value = "org.hibernate.id.uuid.CustomVersionOneStrategy"
                    )
            }
    )
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    private String direccion, telefono, avatar, password, fullName;

    @NaturalId
    @Column(unique = true, updatable = false)
    private String email;

    private UserRoles role;

    @OneToMany(mappedBy = "propietario", orphanRemoval = true)
    private List<Vivienda> viviendas;

    @OneToMany(mappedBy = "gestor", orphanRemoval = true)
    private List<Inmobiliaria> inmobiliarias;

    @OneToMany(mappedBy = "interesado", orphanRemoval = true)
    private List<Interesa> intereses;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
