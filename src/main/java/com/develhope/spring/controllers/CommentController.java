package com.develhope.spring.controllers;

import com.develhope.spring.dtos.requests.CommentRequestDTO;
import com.develhope.spring.dtos.responses.CommentResponseDTO;
import com.develhope.spring.models.Response;
import com.develhope.spring.services.implementations.CommentServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/comments")
public class CommentController {

    private final CommentServiceImpl commentService;

    @Autowired
    public CommentController(CommentServiceImpl commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> getCommentById(@PathVariable Long id) {
        CommentResponseDTO comment = commentService.findCommentById(id)
                .orElseThrow(() -> new EntityNotFoundException("Comment with ID " + id + " not found"));

        return ResponseEntity.ok().body(
                new Response(HttpStatus.OK.value(), "Comment found.", comment)
        );
    }

    @GetMapping
    public ResponseEntity<Response> getAllComments() {
        List<CommentResponseDTO> comments = commentService.getAllComments();

        return ResponseEntity.ok().body(
                new Response(HttpStatus.OK.value(), "Comments found.", comments)
        );
    }

    @PostMapping
    public ResponseEntity<Response> createComment(@RequestBody CommentRequestDTO request) {
        CommentResponseDTO comment = commentService.createComment(request);

        return ResponseEntity.ok().body(
                new Response(HttpStatus.OK.value(), "Comment created successfully.", comment)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response> updateComment(@PathVariable Long id, @RequestBody CommentRequestDTO request) {
        CommentResponseDTO comment = commentService.updateComment(id, request);

        return ResponseEntity.ok().body(
                new Response(HttpStatus.OK.value(), "Comment updated successfully.", comment)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteComment(@PathVariable Long id) {
        boolean isDeleted = commentService.deleteComment(id);
        if (isDeleted) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(
                    new Response(HttpStatus.NO_CONTENT.value(), "Comment deleted successfully.")
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new Response(HttpStatus.NOT_FOUND.value(), "Comment with ID " + id + " not found.")
            );
        }
    }

}

