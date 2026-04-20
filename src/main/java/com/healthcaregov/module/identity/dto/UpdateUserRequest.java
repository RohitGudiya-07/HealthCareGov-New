package com.healthcaregov.module.identity.dto;

import com.healthcaregov.module.identity.entity.User;
import lombok.Data;

@Data
public class UpdateUserRequest {
    private String name;
    private String phone;
    private User.UserStatus status;
}
