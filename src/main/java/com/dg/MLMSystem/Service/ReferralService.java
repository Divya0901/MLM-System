package com.dg.MLMSystem.Service;

import com.dg.MLMSystem.Entity.Referral;
import com.dg.MLMSystem.Entity.UserInfo;
import com.dg.MLMSystem.Repository.ReferralRepository;
import com.dg.MLMSystem.Repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReferralService {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private ReferralRepository referralRepository;

    @Autowired
    private CommissionService commissionService;

    private static final int INITIAL_LEVEL = 1;
    private static final int MAX_LEVEL = 3;

    public void recordReferral(Long referrerId, Long referredUserId) {
        UserInfo referrer = userInfoRepository.findById(referrerId)
                .orElseThrow(() -> new IllegalArgumentException("Referrer not found"));

        UserInfo referredUserInfo = userInfoRepository.findById(referredUserId)
                .orElseThrow(() -> new IllegalArgumentException("Referred user not found"));

        Referral referral = new Referral();
        referral.setReferrer(referrer);
        referral.setReferred(referredUserInfo);
        referral.setLevel(calculateLevel(referrer));

        referralRepository.save(referral);

        referredUserInfo.setReferrer(referrer);

        userInfoRepository.save(referredUserInfo);

        // Update commission amount for the referrer
//        commissionService.updateCommission(referrer, referral);
    }

    private int calculateLevel(UserInfo referrer) {
        int level = INITIAL_LEVEL;
        UserInfo currentReferrer = referrer;

        while (currentReferrer.getReferrer() != null && level < MAX_LEVEL) {
            currentReferrer = currentReferrer.getReferrer();
            level++;
        }

        return level;
    }
}

