package com.spring.vault.service.user;

import com.spring.vault.domain.Credentials;
import com.spring.vault.domain.ERole;
import com.spring.vault.domain.Role;
import com.spring.vault.domain.User;
import com.spring.vault.jwt.CustomUserDetails;
import com.spring.vault.repository.RoleRepository;
import com.spring.vault.repository.UserRepository;
import com.spring.vault.service.credential.CredentialService;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final CredentialService credentialService;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, CredentialService credentialService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.credentialService = credentialService;
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public User addRole(int userId, ERole eRole) {
        User user = userRepository.findById(userId).orElseThrow();
        Role role = roleRepository.findByName(eRole);

        user.getRoles().add(role);
        user.getRoles().forEach(r -> r.setUsers(List.of(user)));

        return userRepository.save(user);
    }

    @Override
    public User removeRole(int userId, ERole eRole) {
        User user = userRepository.findById(userId).orElseThrow();
        Role role = roleRepository.findByName(eRole);

        if (role != null) user.getRoles().remove(role);
        user.getRoles().forEach(r -> r.setUsers(List.of(user)));

        return userRepository.save(user);
    }

    @Override
    public String changePassword(String username, String oldPassword, String newPassword) {
        String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(currentUserName);
        if (user.getUsername().equals(username)){
            Credentials credentials = credentialService.accessCredentials(username);
            if (credentials!=null){
                if (credentials.getPassword().equals(oldPassword)){
                    credentials.setPassword(newPassword);
                    credentialService.secureCredentials(credentials);
                }else throw new BadCredentialsException("Şifreler Eşleşmedi");
            }else throw new BadCredentialsException("Kullanıcı Bilgisi hatalı.");
        }else throw new BadCredentialsException("Kullanıcı adı hatalı");

        return "Şifre bilgisi başarıyla güncellendi";
    }
}
