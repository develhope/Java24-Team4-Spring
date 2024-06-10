package com.develhope.spring.services.interfaces;

import com.develhope.spring.dtos.requests.CommentRequestDTO;
import com.develhope.spring.dtos.responses.CommentResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public interface CommentService {

    CommentResponseDTO createComment(CommentRequestDTO request);

    List<CommentResponseDTO> getAllComments();

    Optional<CommentResponseDTO> findCommentById(Long id);

    CommentResponseDTO updateComment(Long id, CommentRequestDTO request);

    boolean deleteComment(Long id);
}
