package com.healthcaregov.module.identity.dto;

import com.healthcaregov.module.identity.entity.User;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RegisterRequest {
    @NotBlank(message = "Name is required") private String name;
    @NotNull(message = "Role is required") private User.Role role;
    @NotBlank @Email(message = "Valid email required") private String email;
    private String phone;
    @NotBlank @Size(min = 8, message = "Password min 8 chars") private String password;
}
