package com.example.demo.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.example.demo.service.StorageService;

@Service
public class StorageServiceImpl implements StorageService {
    private final AmazonS3 s3client;

    public StorageServiceImpl(AmazonS3 s3client) {
        this.s3client = s3client;
    }

    private String bucketName = "my-mio";

    @Override
    public String uploadFile(MultipartFile multipartFile) throws IOException {
        File file = convertMultiPartFile(multipartFile);
        String fileName = multipartFile.getOriginalFilename();
        s3client.putObject(new PutObjectRequest(bucketName, multipartFile.getOriginalFilename(), file));
        Files.delete(file.toPath());

        //获取文件的URL
        return s3client.getUrl(bucketName, fileName).toString();
    }

    @Override
    public S3Object downloadFile(String fileName) {
        return s3client.getObject(new GetObjectRequest(bucketName, fileName));
    }

    private File convertMultiPartFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

    @Override
    public void deleteFile(String fileName) {
        s3client.deleteObject(new DeleteObjectRequest(bucketName, fileName));
    }
}
