package com.family.api.controller;

import com.family.api.dto.ApiResponse;
import com.family.api.dto.FamilyAddRequest;
import com.family.api.service.FamilyService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/family")
public class FamilyController {

    private final FamilyService familyService;

    @PostMapping("/add")
    public ApiResponse<String> add(@AuthenticationPrincipal UserDetails userDetails,
                                   @RequestBody FamilyAddRequest familyAddRequest){
        String username = userDetails.getUsername();
        familyService.add(familyAddRequest);

        return ApiResponse.<String>builder().data("OK").build();
    }
}
