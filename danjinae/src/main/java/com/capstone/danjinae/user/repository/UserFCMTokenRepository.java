package com.capstone.danjinae.user.repository;

import java.util.List;

import com.capstone.danjinae.user.entity.UserFCMToken;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserFCMTokenRepository extends JpaRepository<UserFCMToken, Integer>{
    UserFCMToken findAllByUserId(Integer userId);
}
