package com.marketplace.marketplace_api.user.controller;

import com.marketplace.marketplace_api.user.dto.CreateUserRequest;
import com.marketplace.marketplace_api.user.dto.UpdateUserRequest;
import com.marketplace.marketplace_api.user.dto.UpdateUserRoleRequest;
import com.marketplace.marketplace_api.user.dto.UserResponse;
import com.marketplace.marketplace_api.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse createUser(@Valid @RequestBody CreateUserRequest request) {
        return userService.createUser(request);
    }

    @GetMapping("/{id}")
    public UserResponse getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @GetMapping
    public Page<UserResponse> getAllUsers(@RequestParam(required = false) Boolean active,
                                          @PageableDefault(size = 10, sort = "id") Pageable pageable)  { // parâmetro opcional & paginação
        return userService.getAllUsers(active, pageable);
    }

    @PutMapping("/{id}")
    public UserResponse updateUser(@PathVariable Long id, @Valid @RequestBody UpdateUserRequest request) {
        return userService.updateUser(id, request);
    }

    @PatchMapping("/{id}/role")
    public UserResponse updateUserRole(@PathVariable Long id, @Valid @RequestBody UpdateUserRoleRequest request) {
        return userService.updateUserRole(id, request);
    }

    @PatchMapping("/{id}/activate")
    public UserResponse updateUserActive(@PathVariable Long id) {
        return userService.activateUser(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}
