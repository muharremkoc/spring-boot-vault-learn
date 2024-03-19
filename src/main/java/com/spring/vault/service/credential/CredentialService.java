package com.spring.vault.service.credential;

import com.spring.vault.domain.Credentials;

import java.util.List;
import java.util.Optional;

public interface CredentialService {

    String secureCredentials(Credentials credentials);
    Credentials accessCredentials(String username);

    List<String> getCredentials();

    void deleteCredentials(String username);
}
