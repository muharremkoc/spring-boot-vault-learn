package com.spring.vault.domain;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.vault.repository.mapping.Secret;

import java.util.List;

@Secret(backend = "kv",value = "pass/users")
@Getter
@Setter
public class Credentials {

    @Id
    private String username;
    private String password;

    public Credentials() {

    }

    public Credentials(String username, String password) {
        this.username = username;
        this.password = password;
    }
    @Override
    public String toString() {
        return "Credential [username=" + username + ", password=" + password + "]";
    }
}
