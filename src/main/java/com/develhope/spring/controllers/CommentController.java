package com.develhope.spring.controllers;

import com.develhope.spring.dtos.requests.CommentRequestDTO;
import com.develhope.spring.dtos.responses.CommentResponseDTO;
import com.develhope.spring.models.Response;
import com.develhope.spring.services.implementations.CommentServiceImpl;
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
        CommentResponseDTO comment = commentService.findCommentById(id);
        return ResponseEntity.ok().body(
                new Response(HttpStatus.OK.toString(), "Comment found.", comment)
        );
    }

    @GetMapping
    public ResponseEntity<Response> getAllComments() {
        List<CommentResponseDTO> comments = commentService.getAllComments();

        return ResponseEntity.ok().body(
                new Response(HttpStatus.OK.toString(), "Comments found.", comments)
        );
    }

    @PostMapping
    public ResponseEntity<Response> createComment(@RequestBody CommentRequestDTO request) {
        CommentResponseDTO comment = commentService.createComment(request);

        return ResponseEntity.ok().body(
                new Response(HttpStatus.OK.toString(), "Comment created successfully.", comment)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response> updateComment(@PathVariable Long id, @RequestBody CommentRequestDTO request) {
        CommentResponseDTO comment = commentService.updateComment(id, request);

        return ResponseEntity.ok().body(
                new Response(HttpStatus.OK.toString(), "Comment updated successfully.", comment)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteComment(@PathVariable Long id) {
        CommentResponseDTO comment = commentService.deleteCommentById(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(
                new Response(HttpStatus.OK.toString(), "Comment deleted successfully.", comment)
        );
    }

}
