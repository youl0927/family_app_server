package com.family.api.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.UUID;

public class LocalFileStorage {
    private static final String basePath = "/Users/yourname/uploads";

    public static String upload(String username, Long boardId, MultipartFile file) throws IOException {

        String fileName = file.getOriginalFilename();

        // 상대경로
        String storageKey = username + "/" + boardId + "/" + fileName;

        File dest = new File(basePath, storageKey);

        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }

        file.transferTo(dest);

        return storageKey; // DB에는 이 값 저장
    }
}
