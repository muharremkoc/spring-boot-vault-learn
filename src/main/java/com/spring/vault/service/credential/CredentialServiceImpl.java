package com.spring.vault.service.credential;

import com.spring.vault.domain.Credentials;
import org.springframework.stereotype.Service;
import org.springframework.vault.core.VaultKeyValueOperations;
import org.springframework.vault.core.VaultKeyValueOperationsSupport;
import org.springframework.vault.core.VaultTemplate;
import org.springframework.vault.support.VaultResponseSupport;

import java.util.List;

@Service
public class CredentialServiceImpl implements CredentialService {


    public static String PATH = "pass/users/";

    private final VaultTemplate vaultTemplate;
    private final VaultKeyValueOperations vaultKeyValueOperations;


    public CredentialServiceImpl(VaultTemplate vaultTemplate) {
        this.vaultTemplate = vaultTemplate;
        this.vaultKeyValueOperations = vaultTemplate.opsForKeyValue("kv", VaultKeyValueOperationsSupport.KeyValueBackend.KV_2);
    }

    @Override
    public String secureCredentials(Credentials credentials) {
        vaultKeyValueOperations.put(PATH + credentials.getUsername(), credentials);
        return "Secret written successfully.";

    }

    @Override
    public Credentials accessCredentials(String username) {
        VaultResponseSupport<Credentials> response = vaultKeyValueOperations.get(PATH + username, Credentials.class);
        if (response != null) {
            return response.getData();
        } else return null;
    }

    @Override
    public List<String> getCredentials() {

        return vaultKeyValueOperations.list(PATH);

    }

    @Override
    public void deleteCredentials(String username) {
        vaultKeyValueOperations.delete(PATH + username);
    }
}
