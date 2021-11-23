package com.salesianostriana.dam.realestatesecurity.users.services;

import com.salesianostriana.dam.realestatesecurity.services.base.BaseService;
import com.salesianostriana.dam.realestatesecurity.users.dto.CreateUserDto;
import com.salesianostriana.dam.realestatesecurity.users.model.UserEntity;
import com.salesianostriana.dam.realestatesecurity.users.model.UserRoles;
import com.salesianostriana.dam.realestatesecurity.users.repositories.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service("userDetailService")
@RequiredArgsConstructor
public class UserEntityService extends BaseService<UserEntity, UUID, UserEntityRepository> implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return this.repositorio.findFirstByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException(email + " no encontrado"));
    }

    public UserEntity save(CreateUserDto newUser, UserRoles role) {
        if(newUser.getPassword().contentEquals(newUser.getPassword2())) {
            UserEntity userEntity = UserEntity.builder()
                    .email(newUser.getEmail())
                    .full_name(newUser.getFull_name())
                    .avatar(newUser.getAvatar())
                    .password(passwordEncoder.encode(newUser.getPassword()))
                    .role(role)
                    .build();
            return save(userEntity);
        }
        else {
            return null;
        }
    }
}
