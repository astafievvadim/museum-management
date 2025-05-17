package com.astafievvadim.mm_backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.io.Serializable;

@Entity
public class FileData{
    @Id
    private String id;

    private long size;

    private String httpContentType;

    public FileData() {
    }

    public FileData(long size, String httpContentType) {
        this.size = size;
        this.httpContentType = httpContentType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getHttpContentType() {
        return httpContentType;
    }

    public void setHttpContentType(String httpContentType) {
        this.httpContentType = httpContentType;
    }
}
