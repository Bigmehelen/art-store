package org.semicolon.semicolonartworksystem.services;

import org.semicolon.semicolonartworksystem.data.models.UserPrincipal;
import org.semicolon.semicolonartworksystem.exceptions.ImageNotFoundExeption;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ArtworkImagesServiceImpl implements ArtworkImageService{

    @Override
    public String upload(MultipartFile file) {
        try {
            String originalFilename = file.getOriginalFilename();
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String fileName = UUID.randomUUID() + extension;

            Path path = Path.of("uploads/artworkImages/");
            if (!Files.exists(path)) Files.createDirectories(path);
            Files.copy(file.getInputStream(), path.resolve(fileName));
            return fileName;
        }catch(IOException ex){
            throw new RuntimeException(ex);
        }
    }

    @Override 
    public Resource download(String file) {
        try{
            Path path = Path.of("uploads/artworkImages/" + file);
            Resource resource = new UrlResource(path.toUri());
            if(resource.exists()) return resource;
            throw new ImageNotFoundExeption();
        }catch(MalformedURLException ex){
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<String> uploadAll(List<MultipartFile> files) {
        List<String> uploadedFileName = new ArrayList<>();
        try{
            Path path = Path.of("uploads/artworkImages/");

            if(!Files.exists(path)) Files.createDirectories(path);
            for(MultipartFile file : files){
                String originalFilename = file.getOriginalFilename();
                if(originalFilename != null && originalFilename.contains(".")){
                    String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
                    String fileName = UUID.randomUUID() + extension;
                    Files.copy(file.getInputStream(),path.resolve(fileName));
                    uploadedFileName.add(fileName);
                }
            }
        }catch(IOException ex){
            throw new RuntimeException(ex);
        }
        return uploadedFileName;
    }
}
