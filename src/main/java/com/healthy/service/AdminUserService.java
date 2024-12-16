package com.healthy.service;
import com.healthy.dto.AuthResponseDTO;
import com.healthy.dto.LoginDTO;
import com.healthy.dto.UserDTO;


public interface AdminUserService {
    UserDTO registerUser(UserDTO user);
    UserDTO updateUser(Integer id, UserDTO user);
    AuthResponseDTO loginUser(LoginDTO loginDTO);
}