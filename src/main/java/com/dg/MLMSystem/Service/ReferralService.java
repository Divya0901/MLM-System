package com.dg.MLMSystem.Service;

import com.dg.MLMSystem.Entity.Referral;
import com.dg.MLMSystem.Entity.User;
import com.dg.MLMSystem.Repository.ReferralRepository;
import com.dg.MLMSystem.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReferralService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReferralRepository referralRepository;

    @Autowired
    private CommissionService commissionService;

    private static final int INITIAL_LEVEL = 1;
    private static final int MAX_LEVEL = 3;

    public void recordReferral(Long referrerId, Long referredUserId) {
        User referrer = userRepository.findById(referrerId)
                .orElseThrow(() -> new IllegalArgumentException("Referrer not found"));

        User referredUser = userRepository.findById(referredUserId)
                .orElseThrow(() -> new IllegalArgumentException("Referred user not found"));

        Referral referral = new Referral();
        referral.setReferrer(referrer);
        referral.setReferred(referredUser);
        referral.setLevel(calculateLevel(referrer));

        referralRepository.save(referral);

        referredUser.setReferrer(referrer);

        userRepository.save(referredUser);

        // Update commission amount for the referrer
//        commissionService.updateCommission(referrer, referral);
    }

    private int calculateLevel(User referrer) {
        int level = INITIAL_LEVEL;
        User currentReferrer = referrer;

        while (currentReferrer.getReferrer() != null && level < MAX_LEVEL) {
            currentReferrer = currentReferrer.getReferrer();
            level++;
        }

        return level;
    }
}

