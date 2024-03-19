package com.spring.vault.repository;

import com.spring.vault.domain.ERole;
import com.spring.vault.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role,Integer> {

    Role findByName(ERole eRole);
}
