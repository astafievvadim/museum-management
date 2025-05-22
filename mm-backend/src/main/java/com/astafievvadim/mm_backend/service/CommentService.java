package com.astafievvadim.mm_backend.service;

import com.astafievvadim.mm_backend.model.Comment;
import com.astafievvadim.mm_backend.model.Post;
import com.astafievvadim.mm_backend.repo.CommentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    private final CommentRepo commentRepository;

    @Autowired
    public CommentService(CommentRepo commentRepository) {
        this.commentRepository = commentRepository;
    }

    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    public Optional<Comment> getCommentById(Long id) {
        return commentRepository.findById(id);
    }

    public List<Comment> getCommentsByPost(Post post) {
        return commentRepository.findByPost(post);
    }

    public Comment createComment(Comment comment) {
        comment.setCreatedAt(new Date());
        return commentRepository.save(comment);
    }

    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }
}
