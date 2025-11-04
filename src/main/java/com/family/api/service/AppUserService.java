package com.family.api.service;

import com.family.api.domain.AppUser;
import com.family.api.dto.LoginRequest;
import com.family.api.dto.LoginResponse;
import com.family.api.jwt.JwtUtil;
import com.family.api.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Iterator;

@Service
@RequiredArgsConstructor
public class AppUserService {
    private final AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Value("${spring.jwt.access}")
    private Long accessTime;

    @Value("${spring.jwt.refresh}")
    private Long refreshTime;

    public void joinProcess(){
        String username = "test";
        String password = "test";

        AppUser appUser = AppUser.builder()
                .username(username)
                .password(bCryptPasswordEncoder.encode(password))
                .name("youl")
                .role("ROLE_USER")
                .build();
        appUserRepository.save(appUser);
    }

    public LoginResponse login(LoginRequest loginRequest){
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());
        Authentication authentication = authenticationManager.authenticate(authToken);

        String username = authentication.getName();
        Iterator<? extends GrantedAuthority> it = authentication.getAuthorities().iterator();
        String role = it.hasNext() ? it.next().getAuthority() : "ROLE_USER";

        String accessToken = jwtUtil.createJwt("access", username, role, accessTime);
        String refreshToken = jwtUtil.createJwt("refresh", username, role, refreshTime);

        return LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
