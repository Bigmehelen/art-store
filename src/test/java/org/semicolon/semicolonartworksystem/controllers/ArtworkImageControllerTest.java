package org.semicolon.semicolonartworksystem.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.semicolon.semicolonartworksystem.dtos.requests.CreateArtworkRequest;
import org.semicolon.semicolonartworksystem.services.ArtworkImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = ArtworkImageController.class, excludeAutoConfiguration =  SecurityAutoConfiguration.class)
class ArtworkImageControllerTest {

    @Autowired
    private ArtworkImageController artworkImageController;

    @MockitoBean
    private ArtworkImageService artworkImageService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testThatCanSaveImage() throws Exception{
        String fileName = UUID.randomUUID() + ".jpg";
        when(artworkImageService.upload(any())).thenReturn(fileName);

        MockMultipartFile  file = new MockMultipartFile("file", "monalisa", MediaType.IMAGE_JPEG_VALUE, "image/jpeg".getBytes());

        mockMvc.perform(multipart("/api/v1/artwork/upload")
                .file(file))
                .andExpect(status().isOk())
                .andExpect(content().string(fileName));
        verify(artworkImageService).upload(any());
    }

    @Test
    public void testThatCanViewImage() throws Exception{
        String fileName = "f86cbfd9-f416-4e7b-8384-fa42403c5db2.jpg";
        Path path = Path.of("uploads/artworkImages/" + fileName);

        Resource resource = new UrlResource(path.toUri());
        when(artworkImageService.download(any())).thenReturn(resource);

        mockMvc.perform(get("/api/v1/artwork/download/{filename}", fileName))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.IMAGE_JPEG_VALUE))
                .andReturn();
    }

    @Test
    public void testThatCanUploadAllArtworkImages() throws Exception{
        MockMultipartFile mockFile = new MockMultipartFile(
                "file",
                "test.jpg",
                "image/jpeg",
                "dummy image content".getBytes()

        );

        MockMultipartFile mockFileTwo = new MockMultipartFile(
                "file",
                "test.jpg",
                "image/jpeg",
                "dummy image content".getBytes()

        );

        List<String> allImages = List.of(UUID.randomUUID() + ".jpg", UUID.randomUUID() + ".jpg");
        when(artworkImageService.uploadAll(any())).thenReturn(allImages);
        mockMvc.perform(multipart("/api/v1/artwork/upload-all")
                .file(mockFile)
                .file(mockFileTwo))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(allImages)));


    }

}