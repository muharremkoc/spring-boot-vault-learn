package com.spring.vault.service.auth;

import com.spring.vault.model.AuthRequest;
import com.spring.vault.model.AuthResponse;

public interface AuthService {

    AuthResponse login(AuthRequest authRequest);

    String register(AuthRequest authRequest);


}
