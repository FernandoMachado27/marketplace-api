package com.marketplace.marketplace_api.user.mapper;

import com.marketplace.marketplace_api.user.dto.UserResponse;
import com.marketplace.marketplace_api.user.entity.User;
import org.springframework.stereotype.Component;

@Component // diz ao Spring gerenciar classe como um Bean
public class UserMapper {

    public UserResponse toResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole(),
                user.getActive(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }

}
