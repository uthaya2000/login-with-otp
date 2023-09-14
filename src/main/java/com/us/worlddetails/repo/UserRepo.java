package com.us.worlddetails.repo;

import com.us.worlddetails.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface UserRepo extends JpaRepository<User, String> {
    @Modifying
    @Query("UPDATE User u SET u.otp = NULL WHERE u.email = :email")
    void clearOtp(@Param("email") String email);
}
