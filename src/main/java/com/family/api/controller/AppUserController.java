package com.family.api.controller;

import com.family.api.dto.ApiResponse;
import com.family.api.service.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class AppUserController {

    public final AppUserService appUserService;

    @PostMapping("/join")
    public ResponseEntity<ApiResponse<String>> joinTest(){
        appUserService.joinProcess();
        ApiResponse<String> result = ApiResponse.<String>builder()
                .data("OK")
                .build();
        return ResponseEntity.ok(result);
    }
}
