package com.astafievvadim.mm_backend.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.core.io.InputStreamResource;

import java.util.Date;
import java.util.Objects;

@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class FileResponse {

    private Long fileId;
    private String filename;
    private String contentType;
    private Long fileSize;
    private Date createdTime;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private transient InputStreamResource stream;

    public FileResponse() {
    }

    public FileResponse(String filename, String contentType, Long fileSize, Date createdTime, InputStreamResource stream, Long fileId) {
        this.fileId = fileId;
        this.filename = filename;
        this.contentType = contentType;
        this.fileSize = fileSize;
        this.createdTime = createdTime;
        this.stream = stream;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public InputStreamResource getStream() {
        return stream;
    }

    public void setStream(InputStreamResource stream) {
        this.stream = stream;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FileResponse that)) return false;
        return filename.equals(that.filename) && contentType.equals(that.contentType) && fileSize.equals(that.fileSize) && createdTime.equals(that.createdTime) && stream.equals(that.stream);
    }

    @Override
    public int hashCode() {
        return Objects.hash(filename, contentType, fileSize, createdTime, stream);
    }

    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }
}
