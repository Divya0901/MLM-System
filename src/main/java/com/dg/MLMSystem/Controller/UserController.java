package com.dg.MLMSystem.Controller;

import com.dg.MLMSystem.DTO.UserDTO;
import com.dg.MLMSystem.DTO.UserRegistrationRequest;
import com.dg.MLMSystem.DTO.UserRoleEnum;
import com.dg.MLMSystem.Entity.Role;
import com.dg.MLMSystem.Entity.User;
import com.dg.MLMSystem.Repository.RoleRepository;
import com.dg.MLMSystem.Repository.UserRepository;
import com.dg.MLMSystem.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody UserDTO userDTO,
                                             @RequestParam Set<UserRoleEnum> roles) {
        User user = userService.registerUser(userDTO, roles);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUser(@PathVariable Long userId) {
        User user = userService.getUserById(userId);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/{userId}/referrals")
    public ResponseEntity<List<User>> getUserReferrals(@PathVariable Long userId) {
        List<User> referrals = userService.getUserReferrals(userId);
        return ResponseEntity.ok(referrals);
    }

    @GetMapping("/{userId}/commissions")
    public ResponseEntity<BigDecimal> getUserCommissions(@PathVariable Long userId) {
        BigDecimal commissions = userService.getUserCommissions(userId);
        return ResponseEntity.ok(commissions);
    }
}

