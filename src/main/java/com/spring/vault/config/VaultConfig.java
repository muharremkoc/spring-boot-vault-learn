package com.spring.vault.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.vault.annotation.VaultPropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.vault.authentication.ClientAuthentication;
import org.springframework.vault.authentication.TokenAuthentication;
import org.springframework.vault.client.VaultEndpoint;
import org.springframework.vault.config.AbstractVaultConfiguration;
import org.springframework.vault.core.VaultTemplate;


@Configuration
public class VaultConfig {

    @Value("${spring.cloud.vault.token}")
    String token;


    public ClientAuthentication clientAuthentication() {
        return new TokenAuthentication(token);
    }

    public VaultEndpoint vaultEndpoint() {
        VaultEndpoint vaultEndpoint = new VaultEndpoint();
        vaultEndpoint.setHost("localhost");
        vaultEndpoint.setPort(8200);
        vaultEndpoint.setScheme("http");
        return vaultEndpoint;
    }

    public VaultTemplate vaultTemplate(){
        return new VaultTemplate(vaultEndpoint(),clientAuthentication());
    }

}
