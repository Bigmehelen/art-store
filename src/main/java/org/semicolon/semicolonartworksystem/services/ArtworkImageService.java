package org.semicolon.semicolonartworksystem.services;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

public interface ArtworkImageService {
    String upload(MultipartFile file);
    Resource download(String file);
    List<String> uploadAll(List<MultipartFile> file);
}
