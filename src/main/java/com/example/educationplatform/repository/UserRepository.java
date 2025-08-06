package com.example.educationplatform.repository;

import com.example.educationplatform.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // 根据用户名查找用户(登录时使用)
    Optional<User> findByUsername(String username);

    //检查用户名是否存在（登录时会使用到）
    boolean existsByUsername(String username);

    // 根据邮箱查找用户（可用于找回密码等功能）
    Optional<User> findByEmail(String email);


}
