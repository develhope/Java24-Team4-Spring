package com.develhope.spring.services.implementations;

import com.develhope.spring.dtos.requests.CommentRequestDTO;
import com.develhope.spring.dtos.responses.CommentResponseDTO;
import com.develhope.spring.entities.Comment;
import com.develhope.spring.entities.Listener;
import com.develhope.spring.entities.Song;
import com.develhope.spring.repositories.CommentRepository;
import com.develhope.spring.repositories.ListenerRepository;
import com.develhope.spring.repositories.SongRepository;
import com.develhope.spring.services.interfaces.CommentService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final SongRepository songRepository;
    private final ListenerRepository listenerRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CommentServiceImpl(
            CommentRepository commentRepository,
            SongRepository songRepository,
            ListenerRepository listenerRepository,
            ModelMapper modelMapper
    ) {
        this.commentRepository = commentRepository;
        this.songRepository = songRepository;
        this.listenerRepository = listenerRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CommentResponseDTO createComment(CommentRequestDTO request) {

        Song song = songRepository.findById(request.getSongId())
                .orElseThrow(() -> new EntityNotFoundException("Song with ID " + request.getSongId() + " not found"));

        Listener listener = listenerRepository.findById(request.getListenerId())
                .orElseThrow(() -> new EntityNotFoundException("Listener with ID " + request.getListenerId() + " not found"));

        Comment comment = new Comment(song, listener, request.getCommentText());

        Comment savedComment = commentRepository.saveAndFlush(comment);
        return modelMapper.map(savedComment, CommentResponseDTO.class);
    }

    @Override
    public List<CommentResponseDTO> getAllComments() {
        return commentRepository.findAll()
                .stream()
                .map(comment -> modelMapper.map(comment, CommentResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CommentResponseDTO> findCommentById(Long id) {
        return commentRepository.findById(id)
                .map(comment -> modelMapper.map(comment, CommentResponseDTO.class));
    }

    @Override
    public CommentResponseDTO updateComment(Long id, CommentRequestDTO request) {
        Comment existingComment = commentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Comment with ID " + id + " not found"));

        Song song = songRepository.findById(request.getSongId())
                .orElseThrow(() -> new EntityNotFoundException("Song with ID " + request.getSongId() + " not found"));

        Listener listener = listenerRepository.findById(request.getListenerId())
                .orElseThrow(() -> new EntityNotFoundException("Listener with ID " + request.getListenerId() + " not found"));

        existingComment.setSong(song);
        existingComment.setListener(listener);
        existingComment.setCommentText(request.getCommentText());

        Comment updatedComment = commentRepository.saveAndFlush(existingComment);
        return modelMapper.map(updatedComment, CommentResponseDTO.class);
    }

    @Override
    public boolean deleteComment(Long id) {
        if (commentRepository.existsById(id)) {
            commentRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
