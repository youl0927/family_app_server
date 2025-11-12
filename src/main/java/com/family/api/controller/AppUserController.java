package com.family.api.controller;

import com.family.api.domain.AppUser;
import com.family.api.dto.ApiResponse;
import com.family.api.dto.LoginRequest;
import com.family.api.dto.LoginResponse;
import com.family.api.jwt.JwtUtil;
import com.family.api.service.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

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
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@RequestBody LoginRequest request){
        ApiResponse<LoginResponse> result = ApiResponse.<LoginResponse>builder()
                .data(appUserService.login(request))
                .build();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/getUser")
    public UserDetails getUserTest(@AuthenticationPrincipal UserDetails userDetails){
        return userDetails;
    }

}
