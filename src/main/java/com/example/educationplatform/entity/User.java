package com.example.educationplatform.entity;

import com.example.educationplatform.enums.RoleEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.lang.annotation.Target;
/**
 * 用户实体类
 */
@Schema(description = "用户实体类")
@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String username;
    /** 用户名字段，唯一且不能为空
     * 注意：实际应用中应避免使用过于简单的用户名
     */

    @Column(nullable = false)
    private String password;
    /** * 密码字段，存储加密后的密码
     * 注意：实际应用中应使用安全的加密算法存储密码
     */

    @Column(unique = true, length = 100)
    private String email;

    @Column(length = 20)
    private String phone;

    //TODO:不理解这个关键字的作用
    @Enumerated(EnumType.STRING)//将枚举类型按字符串存储在数据库中
    @Column(nullable = true,length = 20)
    private RoleEnum role;






}
