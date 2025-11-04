package org.semicolon.semicolonartworksystem.dtos.requests;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class  CreateArtworkRequest {
    private String artworkTitle;
    private String artworkDescription;
    private LocalDate datePainted;
    private Boolean isAvailable;
    private BigDecimal price;
    private List<String> filenames;
}
