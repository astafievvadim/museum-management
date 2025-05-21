package com.astafievvadim.mm_backend.controller;

import com.astafievvadim.mm_backend.controller.FileController;
import com.astafievvadim.mm_backend.payload.FileResponse;
import com.astafievvadim.mm_backend.service.FileMetadataService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FileController.class)
@AutoConfigureMockMvc(addFilters = false) // disables Spring Security filters
public class FileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FileMetadataService fileMetadataService;

    @Test
    public void testUploadFile() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "file", "test.jpg", "image/jpeg", "some-image-content".getBytes());

        when(fileMetadataService.addFile(any(MultipartFile.class), anyLong()))
                .thenReturn(new FileResponse("test.jpg", "image/jpeg", 123L, new Date(), null, 1L));

        mockMvc.perform(multipart("/api/files/upload")
                        .file(file)
                        .param("itemId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.filename").value("test.jpg"))
                .andExpect(jsonPath("$.contentType").value("image/jpeg"));
    }
}

