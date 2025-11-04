package com.family.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    @Builder.Default
    private String msg = "SUCCESS";
    private T data;
}
