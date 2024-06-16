package com.develhope.spring.services.interfaces;

import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.web.multipart.MultipartFile;

public interface MinioService {

    String uploadFile(MultipartFile file, String newFileName, String destinationFolderName, String bucketName);

}
