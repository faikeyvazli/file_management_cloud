//package com.project.filestorage.controller;
//
//import com.project.filestorage.service.BucketService;
//import com.project.filestorage.service.UserFileService;
//import io.minio.MinioClient;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//@RestController
//@RequestMapping("/resource")
//public class FileController {
//
//    private final MinioClient minioClient;
//
//    private final UserFileService userFileService;
//
//    public FileController(MinioClient minioClient, UserFileService userFileService) {
//        this.minioClient = minioClient;
//        this.userFileService = userFileService;
//    }
//
//
//
//    @PostMapping()
//    public ResponseEntity<String> uploadFile(
//            @RequestParam("file") MultipartFile file) {
//        String fileName = file.getOriginalFilename();
//
//        // logic later
//
//        userFileService.uploadFile();
//        try {
//
//
//        } catch (Exception e) {
//
//        }
//    }
//}
