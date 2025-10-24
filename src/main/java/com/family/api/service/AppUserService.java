package com.family.api.service;

import com.family.api.domain.AppUser;
import com.family.api.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppUserService {
    private final AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

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
}
