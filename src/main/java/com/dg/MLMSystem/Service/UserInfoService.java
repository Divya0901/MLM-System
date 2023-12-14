package com.dg.MLMSystem.Service;

import com.dg.MLMSystem.DTO.UserDTO;
import com.dg.MLMSystem.Entity.Commission;
import com.dg.MLMSystem.Entity.Referral;
import com.dg.MLMSystem.Entity.UserInfo;
import com.dg.MLMSystem.Repository.CommissionRepository;
import com.dg.MLMSystem.Repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserInfoService {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private CommissionRepository commissionRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    private UserInfo addUser(UserInfo userInfo) {
        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        return userInfoRepository.save(userInfo);
    }

    public boolean authenticateUser(String username, String password) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        if (authentication.isAuthenticated()) {
            return true;
        } else
            throw new UsernameNotFoundException("invalid user");
    }

    public UserInfo getUserById(Long userId) {
        return userInfoRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("UserInfo not found"));
    }

    public UserInfo registerUser(UserDTO userDTO) {
        UserInfo userInfo = new UserInfo();
        userInfo.setName(userDTO.getUsername());
        userInfo.setEmail(userDTO.getEmail());
        userInfo.setPassword(userDTO.getPassword());
        userInfo.setRoles(userDTO.getRoles());

        // Set userInfo roles
//        Set<Role> userRoles = roles.stream()
//                .map(role -> roleRepository.findByName(role)
//                        .orElseThrow(() -> new IllegalArgumentException("Role not found: " + role)))
//                .collect(Collectors.toSet());
//        userInfo.setRoles(userRoles);

        // Perform additional validations or business logic as needed

//        userInfoRepository.save(userInfo);

        userInfo = addUser(userInfo);

        Commission commission = new Commission();
        commission.setUser(userInfo);
        commission.setAmount(BigDecimal.ZERO); // Initialize commission to zero

        commissionRepository.save(commission);

        return userInfo;
    }

    public List<UserInfo> getUserReferrals(Long userId) {
        List<UserInfo> userInfoList = new ArrayList<>();
        UserInfo userInfo = getUserById(userId);
        List<Referral> referrals = userInfo.getReferrals();
        for (Referral referral : referrals) {
            userInfoList.add(referral.getReferred());
        }
        return userInfoList;
    }

    public BigDecimal getUserCommissions(Long userId) {
        UserInfo userInfo = getUserById(userId);
        Commission commission = commissionRepository.findByUserInfo(userInfo);
        return commission.getAmount();
    }
}

