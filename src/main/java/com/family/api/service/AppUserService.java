package com.family.api.service;

import com.family.api.domain.AppUser;
import com.family.api.dto.LoginRequest;
import com.family.api.dto.LoginResponse;
import com.family.api.jwt.JwtUtil;
import com.family.api.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Iterator;

@Service
@RequiredArgsConstructor
public class AppUserService {
    private final AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtUtil jwtUtil;

    @Value("${spring.jwt.access}")
    private Long accessTime;

    @Value("${spring.jwt.refresh}")
    private Long refreshTime;

    public void joinProcess(){
        String username = "test1";
        String password = "test1";

        AppUser appUser = AppUser.builder()
                .username(username)
                .password(bCryptPasswordEncoder.encode(password))
                .name("youl")
                .role("ROLE_USER")
                .build();
        appUserRepository.save(appUser);
    }

    public LoginResponse login(LoginRequest loginRequest) {

        // 1. 유저 조회 (없으면 401)
        AppUser user = compareUsernameAndPassword(loginRequest.getUsername(), loginRequest.getPassword());

        // 2. 역할(role) 가져오기 (엔티티에 맞게)
        String username = user.getUsername();
        String role = user.getRole(); // 예: "ROLE_USER"

        // 3. JWT 생성
        String accessToken = jwtUtil.createJwt("access", username, role, accessTime);
        String refreshToken = jwtUtil.createJwt("refresh", username, role, refreshTime);

        // 4. 응답 DTO로 반환
        return LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    private AppUser compareUsernameAndPassword(String username, String password){
        return appUserRepository.findByUsername(username)
                .filter(u -> bCryptPasswordEncoder.matches(password,  u.getPassword()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "아이디 또는 비밀번호가 올바르지 않습니다."));
    }
}
