package com.example.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.model.S3Object;

import java.io.IOException;

@Service
public interface StorageService {
    String uploadFile(MultipartFile multipartFile) throws IOException;

    S3Object downloadFile(String fileName);

    void deleteFile(String fileName);
}
