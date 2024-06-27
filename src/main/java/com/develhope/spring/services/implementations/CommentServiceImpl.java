package com.develhope.spring.services.implementations;

import com.develhope.spring.dtos.requests.CommentRequestDTO;
import com.develhope.spring.dtos.responses.CommentResponseDTO;
import com.develhope.spring.dtos.responses.ListenerResponseDTO;
import com.develhope.spring.dtos.responses.SongResponseDTO;
import com.develhope.spring.entities.Comment;
import com.develhope.spring.entities.Listener;
import com.develhope.spring.entities.Song;
import com.develhope.spring.exceptions.EmptyResultException;
import com.develhope.spring.exceptions.NegativeIdException;
import com.develhope.spring.repositories.CommentRepository;
import com.develhope.spring.repositories.ListenerRepository;
import com.develhope.spring.repositories.SongRepository;
import com.develhope.spring.services.interfaces.CommentService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    @Transactional
    public CommentResponseDTO createComment(CommentRequestDTO request) {

        if (request.getListenerId() < 0 || request.getSongId() < 0) {
            throw new NegativeIdException(
                    "[Creation failed] IDs cannot be negative. Listener ID: " + request.getListenerId() + ", Song ID: " + request.getSongId());
        }

        Song song = songRepository.findById(request.getSongId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "[Creation failed] Song with ID " + request.getSongId() + " not found"));

        Listener listener = listenerRepository.findById(request.getListenerId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "[Creation failed] Listener with ID " + request.getListenerId() + " not found"));

        Comment comment = new Comment(song, listener, request.getCommentText());

        Comment savedComment = commentRepository.saveAndFlush(comment);
        CommentResponseDTO commentResponseDTO = modelMapper.map(savedComment, CommentResponseDTO.class);

        commentDtoSetListenerAndSong(listener,song, commentResponseDTO);

        return commentResponseDTO;
    }

    @Override
    public List<CommentResponseDTO> getAllComments() {
        var comments = commentRepository.findAll()
                .stream()
                .map(comment -> {
                    var commentResponseDTO = modelMapper.map(comment, CommentResponseDTO.class);
                    commentDtoSetListenerAndSong(comment.getListener(), comment.getSong(), commentResponseDTO);

                    return commentResponseDTO;
                })
                .toList();

        if (comments.isEmpty()) {
            throw new EmptyResultException("No comments found in the database.");
        } else {
            return comments;
        }
    }

    public CommentResponseDTO findCommentById(Long id) {
        if (id < 0) {
            throw new NegativeIdException(
                    "[Search failed] Comment ID cannot be negative. Now: " + id);
        }
        return commentRepository.findById(id)
                .map(comment -> {
                    var commentResponseDTO = modelMapper.map(comment, CommentResponseDTO.class);
                    commentDtoSetListenerAndSong(comment.getListener(), comment.getSong(), commentResponseDTO);

                    return commentResponseDTO;

                }).orElseThrow(() -> new EntityNotFoundException
                        ("Comment with ID " + id + " not found in the database"));
    }


    @Override
    @Transactional
    public CommentResponseDTO updateComment(Long id, CommentRequestDTO request) {
        if (id < 0) {
            throw new NegativeIdException(
                    "[Update failed] Comment ID cannot be negative. Now: " + id);
        }

        Song song = songRepository.findById(request.getSongId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "[Update failed] Song with ID " + request.getSongId() + " not found"));

        Listener listener = listenerRepository.findById(request.getListenerId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "[Update failed] Listener with ID " + request.getListenerId() + " not found"));

        return commentRepository.findById(id)
                .map(existingComment -> {
                    existingComment.setSong(song);
                    existingComment.setListener(listener);
                    existingComment.setCommentText(request.getCommentText());

                    Comment updatedComment = commentRepository.saveAndFlush(existingComment);
                    var commentResponseDTO = modelMapper.map(updatedComment, CommentResponseDTO.class);
                    commentDtoSetListenerAndSong(updatedComment.getListener(), updatedComment.getSong(), commentResponseDTO);

                    return commentResponseDTO;
                }).orElseThrow(() -> new EntityNotFoundException(
                        "[Update failed] Comment to update with ID " + id + " not found in the database."));
    }

    @Override
    @Transactional
    public CommentResponseDTO deleteCommentById(Long id) {
        if (id < 0) {
            throw new NegativeIdException(
                    "[Delete failed] Comment ID cannot be negative. Now: " + id);
        }

        return commentRepository.findById(id)
                .map(comment -> {
                    commentRepository.deleteById(id);
                    var commentResponseDTO = modelMapper.map(comment, CommentResponseDTO.class);
                    commentDtoSetListenerAndSong(comment.getListener(),comment.getSong(), commentResponseDTO);

                    return commentResponseDTO;
                }).orElseThrow(() -> new EntityNotFoundException(
                        "[Delete failed] Comment with ID " + id + " not found in the database."));

    }


    private void commentDtoSetListenerAndSong(Listener listener,Song song, CommentResponseDTO responseDTO){
        ListenerResponseDTO listenerResponseDTO = new ListenerResponseDTO(listener.getUser());
        SongResponseDTO songResponseDTO =  modelMapper.map(song, SongResponseDTO.class);
        songResponseDTO.setArtistName(song.getAlbum().getArtist().getArtistName());

        responseDTO.setListener(listenerResponseDTO);
        responseDTO.setSong(songResponseDTO);

    }
}
