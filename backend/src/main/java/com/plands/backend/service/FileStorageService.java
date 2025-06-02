package com.plands.backend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class FileStorageService {

    @Value("${file.upload-dir}")
    private String baseDir;

    public String saveProfileImage(MultipartFile file) throws IOException {
        String profileDir = baseDir + File.separator + "profile";
        return saveFile(file, profileDir);
    }

    public String savePostImage(MultipartFile file) throws IOException {
        String otherDir = baseDir + File.separator + "post";
        return saveFile(file, otherDir);
    }

    private String saveFile(MultipartFile file, String dirPath) throws IOException {
        File dir = new File(dirPath);
        if (!dir.exists()) {
            boolean created = dir.mkdirs();
            if (!created) {
                throw new IOException("디렉터리 생성 실패: " + dirPath);
            }
        }

        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename != null && originalFilename.contains(".")
                ? originalFilename.substring(originalFilename.lastIndexOf("."))
                : "";

        String savedFilename = UUID.randomUUID().toString() + extension;
        File savedFile = new File(dir, savedFilename);

        file.transferTo(savedFile);

        // 예: "/uploads/profile/xxxx.png" 형태 반환 (웹 접근 경로)
        return "/uploads/" + (dirPath.endsWith("profile") ? "profile/" : "post/") + savedFilename;
    }
}
