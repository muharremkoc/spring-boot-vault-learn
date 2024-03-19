package com.spring.vault.jwt;


import com.spring.vault.domain.Credentials;
import com.spring.vault.domain.User;
import com.spring.vault.repository.UserRepository;
import com.spring.vault.service.credential.CredentialService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final CredentialService credentialService;

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Credentials credentials = credentialService.accessCredentials(username);

        User user = userRepository.findByUsername(username);

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),credentials.getPassword(),new ArrayList<>());
    }
}
