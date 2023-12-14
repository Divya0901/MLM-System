package com.dg.MLMSystem.Service;

import com.dg.MLMSystem.Entity.Commission;
import com.dg.MLMSystem.Entity.Referral;
import com.dg.MLMSystem.Entity.UserInfo;
import com.dg.MLMSystem.Repository.CommissionRepository;
import com.dg.MLMSystem.Repository.ReferralRepository;
import com.dg.MLMSystem.Repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CommissionService {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private ReferralRepository referralRepository;

    @Autowired
    private CommissionRepository commissionRepository;

    private static final int FIXED_REFERRAL_COMMISSION = 100;

    public void calculateCommissions() {
        List<UserInfo> userInfos = userInfoRepository.findAll();

        for (UserInfo userInfo : userInfos) {
            Commission totalCommission = calculateTotalCommission(userInfo);
            commissionRepository.save(totalCommission);
        }
    }

    public void updateCommission(UserInfo referrer, Referral referral) {
        int level = referral.getLevel();
        BigDecimal commissionRate = getCommissionRate(level);
        BigDecimal referralCommission = calculateReferralCommission(commissionRate);

//        referrer.getCommissions().set(level - 1, referrer.getCommissions().get(level - 1).add(referralCommission));
        userInfoRepository.save(referrer);
    }

    private Commission calculateTotalCommission(UserInfo userInfo) {
        Commission commission = null;
        BigDecimal totalCommission = BigDecimal.ZERO;

        List<Referral> referrals = referralRepository.findByReferrer(userInfo);

        for (Referral referral : referrals) {
            int level = referral.getLevel();
            BigDecimal commissionRate = getCommissionRate(level);
            commission = commissionRepository.findByUserInfo(userInfo);
            BigDecimal referralCommission = commission.getAmount().add(calculateReferralCommission(commissionRate));
            totalCommission = totalCommission.add(referralCommission);
        }

        commission.setAmount(totalCommission);
        return commission;
    }


    private BigDecimal getCommissionRate(int level) {
        // Adjust commission rates based on your business rules
        switch (level) {
            case 1:
                return BigDecimal.valueOf(0.1); // 10% for level 1
            case 2:
                return BigDecimal.valueOf(0.05); // 5% for level 2
            default:
                return BigDecimal.valueOf(0.025); // 2.5% for level more than 2 level
        }
    }

    private BigDecimal calculateReferralCommission(BigDecimal commissionRate) {
        // Add your specific logic for calculating the referral commission
        // This could involve product sales, business volume, etc.
        // For simplicity, let's assume a fixed value for now
        BigDecimal fixedReferralCommission = BigDecimal.valueOf(FIXED_REFERRAL_COMMISSION);
        return fixedReferralCommission.multiply(commissionRate);
    }
}

