package com.marketplace.marketplace_api.user.service;

import com.marketplace.marketplace_api.shared.exception.BusinessException;
import com.marketplace.marketplace_api.user.dto.CreateUserRequest;
import com.marketplace.marketplace_api.user.entity.User;
import com.marketplace.marketplace_api.user.enums.Role;
import com.marketplace.marketplace_api.user.repository.UserRepository;

public class UserService { // Contém as regras de negócio

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository =  userRepository;
    }

    public User createUser(CreateUserRequest request) {
        validateEmailUniqueness(request.getEmail());

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());

        if (request.getRole() == null) {
            user.setRole(Role.CUSTOMER);
        } else {
            user.setRole(request.getRole());
        }

        return userRepository.save(user);
    }

    private void validateEmailUniqueness(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new BusinessException("Email is already registered");
        }
    }
}