package com.develhope.spring.services.implementations;

import com.develhope.spring.entities.Song;
import com.develhope.spring.exceptions.AudioStreamingFailedException;
import com.develhope.spring.exceptions.FilePathIsNullException;
import com.develhope.spring.repositories.SongRepository;
import com.develhope.spring.services.interfaces.AudioStreamingService;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.errors.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Service
public class AudioStreamingServiceImpl implements AudioStreamingService {

    private final MinioClient minioClient;
    private final SongRepository songRepository;

    @Value("${minio.musicBucket}")
    private String musicBucket;

    @Autowired
    public AudioStreamingServiceImpl(MinioClient minioClient, SongRepository songRepository) {
        this.minioClient = minioClient;
        this.songRepository = songRepository;
    }

    @Override
    public void streamAudio(Long songID, HttpServletResponse response) {
        String filepath = findAudio2Stream(songID);

        if (filepath == null) {
            throw new FilePathIsNullException("[Streaming service error] Filepath is null.");
        }

        try (InputStream inputStream = minioClient.getObject(
                GetObjectArgs.builder()
                        .bucket(musicBucket)
                        .object(filepath)
                        .build())) {

            response.setContentType("audio/mpeg"); // TODO: AGGIUNGERE ALTRI FORMATI
            response.setHeader("Accept-Ranges", "bytes");

            IOUtils.copy(inputStream, response.getOutputStream());
            response.flushBuffer();

        } catch (IOException | ServerException | InsufficientDataException | NoSuchAlgorithmException | ErrorResponseException |
                 InvalidKeyException | InvalidResponseException | XmlParserException | InternalException e) {
            throw new AudioStreamingFailedException("[Streaming service error] File: " + filepath + " Caused by: " + e.getClass() + " Error message: " + e.getMessage());
        }
    }

    private String findAudio2Stream(Long songID) {
        Song song2Stream = songRepository.findById(songID)
                .orElseThrow(() -> new EntityNotFoundException("[Streaming error] Audio file with id " + songID + " not found."));

        return song2Stream.getObjectStorageFileName();
    }
}
