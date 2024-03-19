package com.spring.vault.service.auth;

import com.spring.vault.domain.Credentials;
import com.spring.vault.domain.User;
import com.spring.vault.jwt.JwtTokenUtil;
import com.spring.vault.model.AuthRequest;
import com.spring.vault.model.AuthResponse;
import com.spring.vault.repository.UserRepository;
import com.spring.vault.service.credential.CredentialService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService{
    private final Environment env;

    private final CredentialService credentialService;

    private final UserRepository userRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;


    @Override
    public AuthResponse login(AuthRequest authRequest) {
        log.info("Auth Login Service started");
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        Credentials credentials = credentialService.accessCredentials(authRequest.getUsername());
        if (credentials ==null){
            throw  new BadCredentialsException("Geçersiz kullanıcı adı ya da şifre");
        }

       // boolean isPasswordMatch = passwordEncoder.matches(authRequest.getPassword(), credentials.getPassword());
        boolean isPasswordMatch = authRequest.getPassword().equals(credentials.getPassword());
        if (!isPasswordMatch){
            throw new BadCredentialsException("Şifreler eşleşmedi");
        }

        User user = userRepository.findByUsername(credentials.getUsername());

        if (user ==null){
            throw  new BadCredentialsException("Geçersiz kullanıcı adı");
        }


        String accessToken = jwtTokenUtil.generateToken(user);

        AuthResponse response = new AuthResponse(user.getUsername(),accessToken);

        return response;
    }

    @Override
    public String register(AuthRequest authRequest) {
        Credentials credentials = credentialService.accessCredentials(authRequest.getUsername());
        if (credentials ==null){
            throw  new BadCredentialsException("Geçersiz kullanıcı bilgisi girdiniz,Kullanıcının Vault kaydını kontrol ediniz");
        }
        User user = new User();
        user.setUsername(credentials.getUsername());
        userRepository.save(user);
        return "User Register Successfully";
    }
}
