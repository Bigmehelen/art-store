package org.semicolon.semicolonartworksystem.controllers;

import org.semicolon.semicolonartworksystem.dtos.requests.CreateArtworkRequest;
import org.semicolon.semicolonartworksystem.dtos.responses.CreateArtworkResponse;
import org.semicolon.semicolonartworksystem.services.ArtworkImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@RestController
@RequestMapping("/api/v1/artwork")
@CrossOrigin("*")
public class ArtworkImageController{

    @Autowired
    private ArtworkImageService artworkImageService;

    @PostMapping("/upload")
    public ResponseEntity<String> upload(@RequestParam MultipartFile file) {
        return ResponseEntity.ok(artworkImageService.upload(file));
    }

    @GetMapping("/download/{filename}")
    public ResponseEntity<?> download(@PathVariable String filename){
        try {
            Resource resource = artworkImageService.download(filename);
            Path path = resource.getFile().toPath();
            String contentType = Files.probeContentType(path);
            return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
                    .body(artworkImageService.download(filename));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/upload-all")
    public ResponseEntity<?> uploadAll(@RequestParam("file") List<MultipartFile> file){
        return ResponseEntity.ok()
                .body(artworkImageService.uploadAll(file));
    }
}