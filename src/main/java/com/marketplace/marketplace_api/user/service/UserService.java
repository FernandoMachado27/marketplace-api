package com.marketplace.marketplace_api.user.service;

import com.marketplace.marketplace_api.shared.exception.BusinessException;
import com.marketplace.marketplace_api.shared.exception.ResourceNotFoundException;
import com.marketplace.marketplace_api.user.dto.CreateUserRequest;
import com.marketplace.marketplace_api.user.dto.UserResponse;
import com.marketplace.marketplace_api.user.entity.User;
import com.marketplace.marketplace_api.user.enums.Role;
import com.marketplace.marketplace_api.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService { // Contém as regras de negócio

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository =  userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponse createUser(CreateUserRequest request) {
        validateEmailUniqueness(request.getEmail());

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword())); // Senha criptografada

        if (request.getRole() == null) {
            user.setRole(Role.CUSTOMER);
        } else {
            user.setRole(request.getRole());
        }

        User savedUser = userRepository.save(user);

        return new UserResponse(
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getEmail(),
                savedUser.getRole(),
                savedUser.getCreatedAt(),
                savedUser.getUpdatedAt()
        );
    }

    private void validateEmailUniqueness(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new BusinessException("Email is already registered");
        }
    }

    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id)); // se não encontrar lance essa exceção
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }
}