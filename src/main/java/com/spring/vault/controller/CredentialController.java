package com.spring.vault.controller;

import com.spring.vault.domain.Credentials;
import com.spring.vault.service.credential.CredentialService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vault")
@SecurityRequirement(name = "bearerAuth")
@PreAuthorize(value = "hasAuthority('ROLE_SUPER_ADMIN')")
public class CredentialController {

    private final CredentialService credentialService;

    public CredentialController(CredentialService credentialService) {
        this.credentialService = credentialService;
    }

    @PostMapping("/save")
    public String secureCredentials(@RequestBody Credentials credentials){
      return credentialService.secureCredentials(credentials);
    }

    @GetMapping("/access")
    public Credentials accessCredentials(@RequestParam String username){
        return credentialService.accessCredentials(username);
    }

    @GetMapping("")
    public List<String> getCredentials(){
      return credentialService.getCredentials();
    }

    @DeleteMapping("/delete")
    public void deleteCredentials(@RequestParam String username){
         credentialService.deleteCredentials(username);
    }
}
