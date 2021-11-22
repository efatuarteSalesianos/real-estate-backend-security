package com.salesianostriana.dam.realestatesecurity.security;

import com.salesianostriana.dam.realestatesecurity.security.jwt.JwtProvider;
import com.salesianostriana.dam.realestatesecurity.users.model.UserEntity;
import com.salesianostriana.dam.realestatesecurity.security.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final JwtUserConverter jwtUserConverter;

    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {

        Authentication authentication =
                authenticationManager.authenticate(
                   new UsernamePasswordAuthenticationToken(
                           loginDto.getEmail(),
                           loginDto.getPassword()
                   )
                );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateToken(authentication);

        UserEntity user = (UserEntity) authentication.getPrincipal();

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(jwtUserConverter.convertUserToJwtUserResponse(user, jwt));
    }

    @GetMapping("/me")
    public ResponseEntity<?> quienSoy(@AuthenticationPrincipal UserEntity user){
        return ResponseEntity.ok(jwtUserConverter.convertUserToJwtUserResponse(user, null));
    }
}
