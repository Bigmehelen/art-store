package org.semicolon.semicolonartworksystem.data.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Document(collection = "artworks")
public class Artwork {
    @Id
    private String artworkId;
    private String artworkTitle;
    private String artworkDescription;
    private LocalDate datePainted;
    private Boolean isAvailable;
    private BigDecimal price;

    private List<String> imageUrls;

    private Stock stock;
}
