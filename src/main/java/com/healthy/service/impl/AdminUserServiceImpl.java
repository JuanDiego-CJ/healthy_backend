package com.healthy.service.impl;

import com.healthy.Security.TokenProvider;
import com.healthy.Security.UserPrincipal;
import com.healthy.dto.AuthResponseDTO;
import com.healthy.dto.LoginDTO;
import com.healthy.dto.UserDTO;
import com.healthy.exception.BadRequestException;
import com.healthy.exception.ResourceNotFoundException;
import com.healthy.mapper.UserMapper;
import com.healthy.model.entity.User;
import com.healthy.model.enums.Role;
import com.healthy.repository.UserRepository;
import com.healthy.service.AdminUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service

public class AdminUserServiceImpl implements AdminUserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;

    @Transactional
    @Override
    public UserDTO registerUser(UserDTO userDTO){
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        User newUser = userMapper.toEntity(userDTO);
        newUser.setRole(Role.USER);
        return userMapper.toDto(userRepository.save(newUser));
    }

    @Transactional
    @Override
    public UserDTO updateUser(Integer id, UserDTO userDTO){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con el id: "+id));
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setEmail(userDTO.getEmail());
        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    public AuthResponseDTO loginUser(LoginDTO loginDTO) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getUser(), loginDTO.getPassword())
        );

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        User user = userPrincipal.getUser();

        String token = tokenProvider.createAccessToken(authentication);

        AuthResponseDTO authResponseDTO = userMapper.toAuthResponseDTO(user,token);

        return authResponseDTO;
    }
}