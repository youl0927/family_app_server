package com.family.api.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

public class LocalFileStorage {

    private static final String basePath = System.getProperty("user.dir") + "/uploads";

    public static String upload(String username, Long boardId, MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IOException("업로드할 파일이 비어 있습니다.");
        }

        String originalName = file.getOriginalFilename();
        String ext = StringUtils.getFilenameExtension(originalName);

        // UUID로 파일명 변경 (충돌 방지)
        String savedName = UUID.randomUUID().toString()
                + (ext != null ? "." + ext : "");

        // 상대 경로
        String storageKey = username + "/" + boardId + "/" + savedName;

        Path targetPath = Paths.get(basePath, storageKey);

        // 부모 디렉토리 생성 (없으면 생성, 실패하면 예외)
        Files.createDirectories(targetPath.getParent());

        // 파일 저장
        file.transferTo(targetPath.toFile());

        return storageKey; // DB에는 상대경로만 저장
    }
}
