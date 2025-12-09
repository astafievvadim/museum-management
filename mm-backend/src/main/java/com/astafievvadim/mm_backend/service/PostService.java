package com.astafievvadim.mm_backend.service;

import com.astafievvadim.mm_backend.model.Post;
import com.astafievvadim.mm_backend.payload.PostDTO;
import com.astafievvadim.mm_backend.repo.PostRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PostService {

    private final PostRepo postRepository;

    public PostService(PostRepo postRepository) {
        this.postRepository = postRepository;
    }

    public List<Post> findAll() {
        return postRepository.findAll();
    }

    public Optional<Post> findById(Long id) {
        return postRepository.findById(id);
    }

    public Post save(Post post) {
        return postRepository.save(post);
    }

    public void deleteById(Long id) {
        postRepository.deleteById(id);
    }

    // Convert Post entity to DTO
    public PostDTO toDTO(Post post) {
        if (post == null) return null;

        return new PostDTO(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getCreatedAt()
        );
    }

    // Convert DTO to Post entity
    public Post toEntity(PostDTO dto) {
        if (dto == null) return null;

        Post post = new Post();
        post.setId(dto.getId());
        post.setTitle(dto.getTitle());
        post.setContent(dto.getContent());
        post.setCreatedAt(dto.getCreatedAt());

        return post;
    }
}
