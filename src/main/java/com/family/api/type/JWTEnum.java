package com.family.api.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum JWTEnum {
    NO_TOKEN(401),
    ACCESS_TOKEN_EXPIRED(402),
    INVALID_ACCESS_TOKEN(403);

    private final int value;
    public String getKey(){
        return name();
    }
}
