package com.spring.vault.controller;

import com.spring.vault.domain.ERole;
import com.spring.vault.domain.User;
import com.spring.vault.service.user.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@SecurityRequirement(name = "bearerAuth")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ROLE_SUPER_ADMIN','ROLE_ADMIN')")
    public List<User> getUsers(){
        return userService.getUsers();
    }

    @PutMapping("/{id}/add/role")
    @PreAuthorize("hasAnyAuthority('ROLE_SUPER_ADMIN','ROLE_ADMIN')")
    public User addRoleForUser(@PathVariable("id") int id, @RequestParam(name = "role") ERole eRole){
        return userService.addRole(id,eRole);
    }

    @PutMapping("/{id}/remove/role")
    @PreAuthorize("hasAnyAuthority('ROLE_SUPER_ADMIN','ROLE_ADMIN')")
    public User removeRoleForUser(@PathVariable("id") int id,@RequestParam(name = "role") ERole eRole){
        return userService.removeRole(id,eRole);
    }

    @PatchMapping("/changePassword")
    @PreAuthorize(value = "hasAnyAuthority('ROLE_USER','ROLE_ADMIN','ROLE_SUPER_ADMIN')")
    public String changePassword(@RequestParam String username,@RequestParam String oldPassword,@RequestParam String newPassword){
        return userService.changePassword(username, oldPassword, newPassword);
    }
}
