package com.marketplace.marketplace_api.auth.service;

import com.marketplace.marketplace_api.shared.exception.InvalidCredentialsException;
import com.marketplace.marketplace_api.user.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CurrentUserService {

    public User getCurrentUser() { // recupera usuário do UsernamePasswordAuthenticationToken
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() || !(authentication.getPrincipal() instanceof User user)) {
            throw new InvalidCredentialsException("Authenticated user not found");
        }
        return user;
    }

}
