package com.family.api.domain;

import com.family.api.dto.BoardAddRequest;
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
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardId;

    private String title;

    @Lob
    private String contents;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private AppUser user;


    public static Board create(BoardAddRequest boardAddRequest, AppUser appUser){
        return Board.builder()
                .title(boardAddRequest.getTitle())
                .contents(boardAddRequest.getContents())
                .user(appUser)
                .build();
    }
}
