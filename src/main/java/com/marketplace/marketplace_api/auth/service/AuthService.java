package com.marketplace.marketplace_api.auth.service;

import com.marketplace.marketplace_api.auth.dto.LoginRequest;
import com.marketplace.marketplace_api.auth.dto.LoginResponse;
import com.marketplace.marketplace_api.shared.exception.InvalidCredentialsException;
import com.marketplace.marketplace_api.user.mapper.UserMapper;
import com.marketplace.marketplace_api.user.repository.UserRepository;
import com.marketplace.marketplace_api.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByEmailAndActiveTrue(request.getEmail())
                .orElseThrow(() -> new InvalidCredentialsException("Invalid email or password")); // com a exceção que criei

        boolean passwordMatches = passwordEncoder.matches(request.getPassword(), user.getPassword()); // comparação

        if (!passwordMatches) {
            throw new InvalidCredentialsException("Invalid email or password");
        }

        return new LoginResponse("Login sucessful", userMapper.toResponse(user));
    }

}
