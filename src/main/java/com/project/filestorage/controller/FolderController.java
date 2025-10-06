package com.project.filestorage.controller;

import com.project.filestorage.model.User;
import com.project.filestorage.repository.UserRepository;
import com.project.filestorage.service.UserFileService;
import io.minio.MinioClient;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FolderController {

    private final UserFileService userFileService;
    private final UserRepository userRepository;


    public FolderController(UserFileService userFileService, UserRepository userRepository) {
        this.userFileService = userFileService;
        this.userRepository = userRepository;
    }

    @PostMapping("/directory")
    public ResponseEntity<String> createDirectory(@RequestParam String path){

        // we need to pass the id of an uploading user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        userFileService.createFolder(user.getId(), "general");

        return ResponseEntity.ok("File uploaded successfully");
    }
}
