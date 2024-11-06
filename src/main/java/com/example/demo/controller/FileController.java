package com.example.demo.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.model.S3Object;
import com.example.demo.service.S3Service;

@RestController
@RequestMapping("api/v1/files")
public class FileController {
    private final S3Service s3Service;

    public FileController(S3Service s3Service) {
        this.s3Service = s3Service;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("files") MultipartFile[] files) {
        if (files == null || files.length == 0) {
            return ResponseEntity.badRequest().body("No files were uploaded.");
        }

        StringBuilder result = new StringBuilder();
        for (MultipartFile file : files) {
            if (file.isEmpty()) {
                result.append("File is empty: ").append(file.getOriginalFilename()).append("\n");
                continue;
            }
            try {
                String fileUrl = s3Service.uploadFile(file);
                result.append("File uploaded successfully: ")
                        .append(file.getOriginalFilename())
                        .append(",URL: ")
                        .append(fileUrl).append("\n");
            } catch (IOException exception) {
                return ResponseEntity.internalServerError().body(
                        "Failed to upload file: " + file.getOriginalFilename() + "Error: " + exception.getMessage());
            }
        }
        return ResponseEntity.ok(result.toString());
    }

    @GetMapping("/download/{fileName}")
    public ResponseEntity<InputStreamResource> downloadFile(@PathVariable("fileName") String fileName) {
        S3Object s3Object = s3Service.downloadFile(fileName);
        InputStreamResource resource = new InputStreamResource(s3Object.getObjectContent());
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(s3Object.getObjectMetadata().getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .body(resource);
    }

    @DeleteMapping("/delete/{fileName}")
    public ResponseEntity<String> deleteFile(@PathVariable("fileName") String fileName) {
        try {
            s3Service.deleteFile(fileName);
            return ResponseEntity.ok("File deleted successfully: " + fileName);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Failed to delete file: " + e.getMessage());
        }
    }
}
