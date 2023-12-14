package com.dg.MLMSystem.Repository;

import com.dg.MLMSystem.Entity.Commission;
import com.dg.MLMSystem.Entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommissionRepository extends JpaRepository<Commission, Integer> {
    Commission findByUserInfo(UserInfo userInfo);
}
