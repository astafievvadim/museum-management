package com.astafievvadim.mm_backend.controller;

import com.astafievvadim.mm_backend.model.Comment;
import com.astafievvadim.mm_backend.payload.CommentDTO;
import com.astafievvadim.mm_backend.model.Post;
import com.astafievvadim.mm_backend.service.CommentService;
import com.astafievvadim.mm_backend.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;
    private final PostService postService;

    @Autowired
    public CommentController(CommentService commentService, PostService postService) {
        this.commentService = commentService;
        this.postService = postService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
    public List<CommentDTO> getAllComments() {
        return commentService.getAllComments()
                .stream()
                .map(commentService::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
    public CommentDTO getCommentById(@PathVariable Long id) {
        return commentService.getCommentById(id)
                .map(commentService::toDTO)
                .orElseThrow(() -> new RuntimeException("Comment not found with id: " + id));
    }

    @GetMapping("/post/{postId}")
    public List<CommentDTO> getCommentsByPost(@PathVariable Long postId) {
        Optional<Post> post = postService.findById(postId);
        return commentService.getCommentsByPost(post)
                .stream()
                .map(commentService::toDTO)
                .collect(Collectors.toList());
    }

    @PostMapping
    public CommentDTO createComment(@RequestBody CommentDTO commentDTO) {
        Comment comment = commentService.toEntity(commentDTO);
        Comment saved = commentService.createComment(comment);
        return commentService.toDTO(saved);
    }

    @DeleteMapping("/{id}")
    public void deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
    }
}
