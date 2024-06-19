package com.develhope.spring.services.interfaces;

import jakarta.servlet.http.HttpServletResponse;

public interface AudioStreamingService {
    void streamAudio(Long songID, HttpServletResponse response);
}
