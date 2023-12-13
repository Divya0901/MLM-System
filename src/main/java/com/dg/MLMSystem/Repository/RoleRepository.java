package com.dg.MLMSystem.Repository;

import com.dg.MLMSystem.DTO.UserRoleEnum;
import com.dg.MLMSystem.Entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(UserRoleEnum name);
}
