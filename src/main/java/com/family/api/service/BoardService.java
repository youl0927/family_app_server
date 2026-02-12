package com.family.api.service;

import com.family.api.domain.AppUser;
import com.family.api.domain.Board;
import com.family.api.domain.BoardFile;
import com.family.api.domain.MimeType;
import com.family.api.dto.BoardAddRequest;
import com.family.api.repository.AppUserRepository;
import com.family.api.repository.BoardFileRepository;
import com.family.api.repository.BoardRepository;
import com.family.api.util.LocalFileStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final AppUserRepository appUserRepository;
    private final BoardRepository boardRepository;
    private final BoardFileRepository boardFileRepository;


    public void create(BoardAddRequest boardAddRequest, List<MultipartFile> files, String username) throws IOException {
        AppUser user = appUserRepository.findByUsername(username).orElseThrow();
        Board board = boardAddRequest.board(boardAddRequest, user);
        Board saveBoard = boardRepository.save(board);

        for(MultipartFile file : files){

            String originalName = file.getOriginalFilename();
            String extension = null;

            if (originalName != null && originalName.contains(".")) {
                extension = originalName.substring(originalName.lastIndexOf(".") + 1);
            }
            String contentType = file.getContentType();
            MimeType mimeType = null;
            if (contentType != null) {
                if (contentType.startsWith("image/")) {
                    mimeType = MimeType.image;
                } else if (contentType.startsWith("video/")) {
                    mimeType = MimeType.video;
                }
            }

            String storageKey = LocalFileStorage.upload(user.getUsername(), saveBoard.getBoardId(), file);

            BoardFile boardFile = BoardFile.builder()
                    .fileName(file.getOriginalFilename())
                    .storageKey(storageKey)
                    .mimeType(mimeType) // 일단 단순하게
                    .extension(extension) // 단순 예시
                    .size(file.getSize())
                    .board(saveBoard)
                    .user(user)
                    .family(user.getFamily())
                    .build();

            boardFileRepository.save(boardFile);
        }
    }
}
