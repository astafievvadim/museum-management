package com.astafievvadim.mm_backend.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 2000, nullable = false)
    private String text;

    @ManyToOne
    @JoinColumn(name = "authorId", nullable = false)
    private Customer author;

    @ManyToOne
    @JoinColumn(name = "postId", nullable = false)
    private Post post;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    public Comment() {
    }

    public Comment(String text, Customer author, Post post, Date createdAt) {
        this.text = text;
        this.author = author;
        this.post = post;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Customer getAuthor() {
        return author;
    }

    public void setAuthor(Customer author) {
        this.author = author;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
