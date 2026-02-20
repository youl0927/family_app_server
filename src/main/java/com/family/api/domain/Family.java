package com.family.api.domain;

import com.family.api.dto.FamilyAddRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Family extends BaseEntity{

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long familyId;

    private String familyName;

    private String familyCode;

    public static Family add(FamilyAddRequest familyAddRequest){
        return Family.builder()
                .familyName(familyAddRequest.getFamilyName())
                .familyCode(familyAddRequest.getFamilyCode())
                .build();
    }
}
