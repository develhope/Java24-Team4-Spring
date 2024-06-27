package com.develhope.spring.services.interfaces;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface MinioService {

    Map<String, String> uploadFile(MultipartFile file, String newFileName, String destinationFolderName, String bucketName);

    boolean deleteFile(String bucketName, String filepath);

}
