package com.spring.vault.controller;

import com.spring.vault.model.AuthRequest;
import com.spring.vault.model.AuthResponse;
import com.spring.vault.service.auth.AuthService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public AuthResponse authResponse(@RequestBody AuthRequest authRequest){
        return authService.login(authRequest);
    }

    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAnyAuthority('ROLE_SUPER_ADMIN','ROLE_ADMIN')")
    @PostMapping("/register")
    public String register(@RequestBody AuthRequest authRequest){
        return authService.register(authRequest);
    }
}
