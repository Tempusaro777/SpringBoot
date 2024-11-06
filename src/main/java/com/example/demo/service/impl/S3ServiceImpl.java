package com.example.demo.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

import com.amazonaws.services.s3.model.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.example.demo.service.S3Service;

@Service
public class S3ServiceImpl implements S3Service {
    private final AmazonS3 s3client;

    public S3ServiceImpl(AmazonS3 s3client) {
        this.s3client = s3client;
    }

    private final String bucketName = "my-mio";

    @Override
    public String uploadFile(MultipartFile multipartFile) throws IOException {
//        File file = convertMultiPartFile(multipartFile);
        String fileName = multipartFile.getOriginalFilename();
        if (!isValidVideoFile(fileName)) {
            throw new IOException("Invalid video file format");
        }
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(multipartFile.getSize());
        objectMetadata.setContentType(multipartFile.getContentType());
        s3client.putObject(new PutObjectRequest(bucketName, fileName, multipartFile.getInputStream(), objectMetadata));

        //获取文件的URL
        return s3client.getUrl(bucketName, fileName).toString();
    }

    private boolean isValidVideoFile(String fileName) {
        String[] validExtensions = {".mp4", ".avi", ".mov", ".mkv"};
        for (String extension : validExtensions) {
            if (fileName.toLowerCase().endsWith(extension)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public S3Object downloadFile(String fileName) {
        return s3client.getObject(new GetObjectRequest(bucketName, fileName));
    }

//    private File convertMultiPartFile(MultipartFile file) throws IOException {
//        File convFile = new File(file.getOriginalFilename());
//        FileOutputStream fos = new FileOutputStream(convFile);
//        fos.write(file.getBytes());
//        fos.close();
//        return convFile;
//    }
//
    @Override
    public void deleteFile(String fileName) {
        s3client.deleteObject(new DeleteObjectRequest(bucketName, fileName));
    }
}
