package com.astafievvadim.mm_backend.service;

import com.astafievvadim.mm_backend.model.Comment;
import com.astafievvadim.mm_backend.model.Post;
import com.astafievvadim.mm_backend.payload.CommentDTO;  // assuming this exists
import com.astafievvadim.mm_backend.repo.CommentRepo;
import com.astafievvadim.mm_backend.repo.PostRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CommentService {

    private final CommentRepo commentRepository;
    private final PostRepo postRepo;

    @Autowired
    public CommentService(CommentRepo commentRepository, PostRepo postRepo) {
        this.commentRepository = commentRepository;
        this.postRepo = postRepo;
    }

    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    public Optional<Comment> getCommentById(Long id) {
        return commentRepository.findById(id);
    }

    public List<Comment> getCommentsByPost(Optional<Post> post) {
        return commentRepository.findByPost(post);
    }

    public Comment createComment(Comment comment) {
        comment.setCreatedAt(new Date());
        return commentRepository.save(comment);
    }

    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }

    // Convert Comment entity to CommentDTO
    public CommentDTO toDTO(Comment comment) {
        if (comment == null) return null;
        return new CommentDTO(
                comment.getId(),
                comment.getText(),
                comment.getAuthor().getId(),
                comment.getPost().getId(),
                comment.getCreatedAt()
        );
    }

    // Convert CommentDTO to Comment entity
    public Comment toEntity(CommentDTO dto) {
        if (dto == null) return null;

        Comment comment = new Comment();
        comment.setText(dto.getText());
        comment.setCreatedAt(dto.getCreatedAt());

        if (dto.getPostId() != null) {
            Post post = postRepo.findById(dto.getPostId())
                    .orElseThrow(() -> new RuntimeException("Post not found with id: " + dto.getPostId()));
            comment.setPost(post);
        } else {
            comment.setPost(null);
        }

        return comment;
    }
}
