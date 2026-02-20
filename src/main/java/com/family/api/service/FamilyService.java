package com.family.api.service;

import com.family.api.domain.AppUser;
import com.family.api.domain.Family;
import com.family.api.dto.FamilyAddRequest;
import com.family.api.repository.AppUserRepository;
import com.family.api.repository.FamilyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class FamilyService {

    private final FamilyRepository familyRepository;
    private final AppUserRepository appUserRepository;

    @Transactional
    public void add(FamilyAddRequest familyAddRequest, String username){
        Family family = Family.add(familyAddRequest);
        familyRepository.save(family);
        AppUser appUser = appUserRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("user not found"));
        appUser.add(family);
    }
}
