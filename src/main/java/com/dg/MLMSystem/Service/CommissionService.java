package com.dg.MLMSystem.Service;

import com.dg.MLMSystem.Entity.Referral;
import com.dg.MLMSystem.Entity.User;
import com.dg.MLMSystem.Repository.ReferralRepository;
import com.dg.MLMSystem.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class CommissionService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReferralRepository referralRepository;

    private static final int FIXED_REFERRAL_COMMISSION = 100;

    public void calculateCommissions() {
        List<User> users = userRepository.findAll();

        for (User user : users) {
            BigDecimal totalCommission = calculateTotalCommission(user);
            user.setCommissionAmount(totalCommission);
            userRepository.save(user);
        }
    }

    public void updateCommission(User referrer, Referral referral) {
        int level = referral.getLevel();
        BigDecimal commissionRate = getCommissionRate(level);
        BigDecimal referralCommission = calculateReferralCommission(commissionRate);

//        referrer.getCommissions().set(level - 1, referrer.getCommissions().get(level - 1).add(referralCommission));
        userRepository.save(referrer);
    }

    private BigDecimal calculateTotalCommission(User user) {
        BigDecimal totalCommission = BigDecimal.ZERO;

        List<Referral> referrals = referralRepository.findByReferrer(user);

        for (Referral referral : referrals) {
            int level = referral.getLevel();
            BigDecimal commissionRate = getCommissionRate(level);
            BigDecimal referralCommission = user.getCommissionAmount().add(calculateReferralCommission(commissionRate));
            totalCommission = totalCommission.add(referralCommission);
        }

        return totalCommission;
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

