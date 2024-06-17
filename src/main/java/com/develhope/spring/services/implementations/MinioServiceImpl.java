package com.develhope.spring.services.implementations;

import com.develhope.spring.exceptions.MinIOFileUploadException;
import com.develhope.spring.services.interfaces.MinioService;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.*;
import io.minio.http.Method;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@Service
public class MinioServiceImpl implements MinioService {

    private final MinioClient minioClient;


    @Autowired
    public MinioServiceImpl(MinioClient minioClient) {
        this.minioClient = minioClient;
    }

    //todo: removeFile e downloadFile methods

    @Override
    public String uploadFile(MultipartFile file, String newFileName, String destinationFolderName, String bucketName) {

        String extension = Objects.requireNonNull(FilenameUtils.getExtension(file.getOriginalFilename())).toLowerCase();
        String objectFullName = destinationFolderName + "/" + UUID.randomUUID() +
                "_" + LocalDate.now() + "_" + newFileName + "." + extension;

        String contentType = file.getContentType();
        InputStream fileInputStream = null;

        // Dimensione max di un pezzo (uploader divide i file a pezzi da 10MB
        // ad.es.: dimensione di file = 100mb, dim. di ogni pezzo per = 10)
        long maxPartSize = 10485760L;

        try {

            fileInputStream = file.getInputStream();

            Object newFile = minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(objectFullName)
                    .stream(fileInputStream, -1, maxPartSize)
                    .contentType(contentType)
                    .build());

            return minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.GET)
                            .bucket(bucketName)
                            .object(objectFullName)
                            .build()
            );

        } catch (ErrorResponseException | InsufficientDataException | InternalException | InvalidKeyException |
                 InvalidResponseException | NoSuchAlgorithmException | ServerException | XmlParserException |
                 IOException exception) {

            //todo aggiungere in ex. handler
            throw new MinIOFileUploadException("[File upload error] " + exception.getMessage());

        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
