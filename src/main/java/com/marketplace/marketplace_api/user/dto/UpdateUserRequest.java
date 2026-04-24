package com.marketplace.marketplace_api.user.dto;

import com.marketplace.marketplace_api.user.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdateUserRequest {

    @NotBlank(message = "Name is required")
    @Size(max = 150, message = "Name must have at most 150 characters")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    @Size(max = 150, message = "Emails must have at most 150 characters")
    private String email;

    private Role role;
}
