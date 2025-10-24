package com.family.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;

@AllArgsConstructor
@Builder
public class ApiResponse<T> {
    private String msg = "SUCCESS";
    private T data;
}
