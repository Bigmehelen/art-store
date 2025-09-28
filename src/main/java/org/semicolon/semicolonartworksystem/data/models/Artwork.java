package org.semicolon.semicolonartworksystem.data.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Document(collection = "artworks")
public class Artwork{
    @Id
    private String artworkId;
    private String artworkTitle;
    private String artworkDescription;
    private String artworkImageUrl;
    private LocalDate datePainted;
    private Boolean isAvailable;
    private BigDecimal price;

    private Stock stock;
}
