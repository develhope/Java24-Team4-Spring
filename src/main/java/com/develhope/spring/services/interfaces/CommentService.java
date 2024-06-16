package com.develhope.spring.services.interfaces;

import com.develhope.spring.dtos.requests.CommentRequestDTO;
import com.develhope.spring.dtos.responses.CommentResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public interface CommentService {

    CommentResponseDTO createComment(CommentRequestDTO request);

    List<CommentResponseDTO> getAllComments();

    CommentResponseDTO findCommentById(Long id);

    CommentResponseDTO updateComment(Long id, CommentRequestDTO request);

    CommentResponseDTO deleteCommentById(Long id);
}

