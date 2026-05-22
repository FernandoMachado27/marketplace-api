package com.marketplace.marketplace_api.auth.dto;

import com.marketplace.marketplace_api.user.dto.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponse {

    private String message;
    private UserResponse user;
}
