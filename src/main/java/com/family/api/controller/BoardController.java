package com.family.api.controller;

import com.family.api.dto.ApiResponse;
import com.family.api.dto.BoardAddRequest;
import com.family.api.repository.BoardFileRepository;
import com.family.api.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

    @PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<String>> add(@AuthenticationPrincipal UserDetails userDetails,
                                                   @RequestPart("board")BoardAddRequest boardAddRequest,
                                                   @RequestPart(value = "files")List<MultipartFile> files) throws IOException {
        String username = userDetails.getUsername();
        boardService.create(boardAddRequest, files, username);
        ApiResponse<String> result = ApiResponse.<String>builder()
                .data("OK")
                .build();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
