package com.healthcaregov.module.appointment.dto;

import lombok.*;

@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class ApiResponse<T> {
    private boolean success;
    private String message;
    private T data;

    public static <T> ApiResponse<T> success(String m, T d) { return new ApiResponse<>(true, m, d); }
    public static <T> ApiResponse<T> error(String m) { return new ApiResponse<>(false, m, null); }
}
