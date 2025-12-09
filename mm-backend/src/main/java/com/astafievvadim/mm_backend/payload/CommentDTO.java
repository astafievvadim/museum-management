package com.astafievvadim.mm_backend.payload;

import java.util.Date;

public class CommentDTO {

    private Long id;
    private String text;
    private Long authorId;
    private Long postId;
    private Date createdAt;

    public CommentDTO() {}

    public CommentDTO(Long id, String text, Long authorId, Long postId, Date createdAt) {
        this.id = id;
        this.text = text;
        this.authorId = authorId;
        this.postId = postId;
        this.createdAt = createdAt;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
