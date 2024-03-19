package com.spring.vault.loader;

import com.spring.vault.domain.ERole;
import com.spring.vault.domain.User;
import com.spring.vault.domain.Role;
import com.spring.vault.repository.RoleRepository;
import com.spring.vault.repository.UserRepository;
import com.spring.vault.service.credential.CredentialService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Slf4j
public class DbLoader implements ApplicationRunner {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CredentialService credentialService;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        if (roleRepository.count() == 0)
            Arrays.stream(ERole.values()).map(Role::new).forEach(role -> roleRepository.save(role));

        if (userRepository.count() == 0) {

            credentialService.getCredentials().forEach(s -> {
                userRepository.save(new User(s));
            });

        }


    }
}