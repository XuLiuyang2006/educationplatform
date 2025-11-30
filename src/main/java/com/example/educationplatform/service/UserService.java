package com.example.educationplatform.service;

import com.example.educationplatform.dto.LoginDTO;
import com.example.educationplatform.dto.UserDTO;
import com.example.educationplatform.dto.UserRegisterDTO;
import com.example.educationplatform.entity.User;

public interface UserService {

    void register(UserRegisterDTO userRegisterDTO);

    UserDTO login(LoginDTO loginDTO);

    UserDTO getById(Long Id);

    void update(User user);

}
