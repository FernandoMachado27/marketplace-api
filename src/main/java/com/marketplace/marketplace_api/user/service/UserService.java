package com.marketplace.marketplace_api.user.service;

import com.marketplace.marketplace_api.shared.exception.BusinessException;
import com.marketplace.marketplace_api.shared.exception.ResourceNotFoundException;
import com.marketplace.marketplace_api.user.dto.*;
import com.marketplace.marketplace_api.user.entity.User;
import com.marketplace.marketplace_api.user.enums.Role;
import com.marketplace.marketplace_api.user.mapper.UserMapper;
import com.marketplace.marketplace_api.user.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService { // Contém as regras de negócio

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, UserMapper userMapper) {
        this.userRepository =  userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    public UserResponse createUser(CreateUserRequest request) {
        validateEmailUniqueness(request.getEmail()); // valida se o email já existe no banco

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

        return userMapper.toResponse(savedUser);
    }

    private void validateEmailUniqueness(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new BusinessException("Email is already registered");
        }
    }

    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id)); // se não encontrar lance essa exceção
        return userMapper.toResponse(user);
    }

    public Page<UserResponse> getAllUsers(Boolean active, Pageable pageable) {
        Page<User> users;

        if (active == null) {
            users = userRepository.findAll(pageable);
        } else {
            users = userRepository.findByActive(active, pageable);
        }

        return users.map(userMapper::toResponse);
    }

    public UserResponse updateUser(Long id, UpdateUserRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        validateEmailUniquenessForUpdate(request.getEmail(), user.getId());

        user.setName(request.getName());
        user.setEmail(request.getEmail());

        User updateUser = userRepository.save(user);
        return userMapper.toResponse(updateUser);
    }

    private void validateEmailUniquenessForUpdate(String email, Long userId) {
        userRepository.findByEmail(email)
                .ifPresent(existingUser -> {
                    if (!existingUser.getId().equals(userId)) {
                        throw new BusinessException("Email is already registered");
                    }
                });
    }

    public void deleteUser(Long id) {
        deactivateUser(id);
    }

    public UserResponse updateUserRole(Long id, UpdateUserRoleRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        user.setRole(request.getRole());

        User updatedUser = userRepository.save(user);

        return userMapper.toResponse(updatedUser);
    }

    public UserResponse activateUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        if (Boolean.TRUE.equals(user.getActive())) {
            return userMapper.toResponse(user);
        }

        user.setActive(true);

        User updatedUser = userRepository.save(user);

        return userMapper.toResponse(updatedUser);
    }

    public UserResponse deactivateUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        if (Boolean.FALSE.equals(user.getActive())) {
            return userMapper.toResponse(user);
        }

        user.setActive(false);

        User updatedUser = userRepository.save(user);

        return userMapper.toResponse(updatedUser);
    }
}