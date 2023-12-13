package com.dg.MLMSystem.Repository;


import com.dg.MLMSystem.Entity.Referral;
import com.dg.MLMSystem.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReferralRepository extends JpaRepository<Referral, Long> {
    List<Referral> findByReferrer(User user);
}
