package com.family.api.dto;

import com.family.api.domain.AppUser;
import com.family.api.domain.Board;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardAddRequest {
    private String title;
    private String contents;

    public Board board(BoardAddRequest boardAddRequest, AppUser appUser){
        return Board.builder()
                .title(boardAddRequest.getTitle())
                .contents(boardAddRequest.getContents())
                .user(appUser)
                .build();
    }
}
