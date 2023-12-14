package com.dg.MLMSystem.Controller;

import com.dg.MLMSystem.Entity.UserInfo;
import com.dg.MLMSystem.Service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    @GetMapping("/{userId}")
//    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<UserInfo> getUser(@PathVariable Long userId) {
        UserInfo userInfo = userInfoService.getUserById(userId);
        return ResponseEntity.ok(userInfo);
    }

    @GetMapping("/{userId}/referrals")
    public ResponseEntity<List<UserInfo>> getUserReferrals(@PathVariable Long userId) {
        List<UserInfo> referrals = userInfoService.getUserReferrals(userId);
        return ResponseEntity.ok(referrals);
    }

    @GetMapping("/{userId}/commissions")
    public ResponseEntity<BigDecimal> getUserCommissions(@PathVariable Long userId) {
        BigDecimal commissions = userInfoService.getUserCommissions(userId);
        return ResponseEntity.ok(commissions);
    }
}

