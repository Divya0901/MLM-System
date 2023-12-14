package com.dg.MLMSystem.Repository;


import com.dg.MLMSystem.Entity.UserInfo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {
    UserInfo findByEmail(String email);

    Optional<UserInfo> findByName(String name);
}
