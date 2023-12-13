package com.dg.MLMSystem.Service;

import com.dg.MLMSystem.DTO.UserDTO;
import com.dg.MLMSystem.DTO.UserRoleEnum;
import com.dg.MLMSystem.Entity.Referral;
import com.dg.MLMSystem.Entity.Role;
import com.dg.MLMSystem.Entity.User;
import com.dg.MLMSystem.Repository.RoleRepository;
import com.dg.MLMSystem.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    public User registerUser(UserDTO userDTO, Set<UserRoleEnum> roles) {
        User user = new User();
        user.setUserName(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setCommissionAmount(BigDecimal.ZERO); // Initialize commission to zero

        // Set user roles
        Set<Role> userRoles = roles.stream()
                .map(role -> roleRepository.findByName(role)
                        .orElseThrow(() -> new IllegalArgumentException("Role not found: " + role)))
                .collect(Collectors.toSet());
        user.setRoles(userRoles);

        // Perform additional validations or business logic as needed

        return userRepository.save(user);
    }

    public List<User> getUserReferrals(Long userId) {
        List<User> userList = new ArrayList<>();
        User user = getUserById(userId);
        List<Referral> referrals = user.getReferrals();
        for (Referral referral : referrals) {
            userList.add(referral.getReferred());
        }
        return userList;
    }

    public BigDecimal getUserCommissions(Long userId) {
        User user = getUserById(userId);
        return user.getCommissionAmount();
    }
}

