package com.family.api.service;

import com.family.api.domain.Family;
import com.family.api.dto.FamilyAddRequest;
import com.family.api.repository.FamilyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FamilyService {

    private final FamilyRepository familyRepository;

    @Transactional
    public void add(FamilyAddRequest familyAddRequest){
        Family family = Family.add(familyAddRequest);
        familyRepository.save(family);
    }
}
