package com.dg.MLMSystem.Controller;

import com.dg.MLMSystem.Repository.ReferralRepository;
import com.dg.MLMSystem.Service.ReferralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/referrals")
public class ReferralController {

    @Autowired
    private ReferralService referralService;

    @PostMapping("/record")
    public ResponseEntity<String> recordReferral(
            @RequestParam Long referrerId,
            @RequestParam Long referredUserId) {
        referralService.recordReferral(referrerId, referredUserId);
        return ResponseEntity.ok("Referral recorded successfully");
    }


}
