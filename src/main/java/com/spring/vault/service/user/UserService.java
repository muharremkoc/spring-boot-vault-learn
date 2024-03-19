package com.spring.vault.service.user;

import com.spring.vault.domain.ERole;
import com.spring.vault.domain.User;

import java.util.List;

public interface UserService {

    List<User> getUsers();
    User addRole(int userId, ERole eRole);
    User removeRole(int userId, ERole eRole);

    String changePassword(String username,String oldPassword,String newPassword);
}
