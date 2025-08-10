package com.example.educationplatform.repository;

import com.example.educationplatform.entity.User;
import com.example.educationplatform.enums.RoleEnum;
import com.example.educationplatform.enums.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // 根据用户名查找用户(登录时使用)
    Optional<User> findByUsername(String username);

    //检查用户名是否存在（登录时会使用到）
    boolean existsByUsername(String username);

    // 根据邮箱查找用户（可用于找回密码等功能）
    Optional<User> findByEmail(String email);

    List<User> findByRole(RoleEnum role);// 根据角色查找用户

    List<User> findByStatus(UserStatus status);// 根据状态查找用户

    long countByRole(RoleEnum role); // 统计特定角色的用户数量

    long countByCreateTimeBetween(LocalDateTime start, LocalDateTime end);// 统计在指定时间范围内创建的用户数量
}
