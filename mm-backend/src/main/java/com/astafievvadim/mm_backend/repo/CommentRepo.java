package com.astafievvadim.mm_backend.repo;

import com.astafievvadim.mm_backend.model.Comment;
import com.astafievvadim.mm_backend.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepo extends JpaRepository<Comment, Long> {
    List<Comment> findByPost(Post post);
}
