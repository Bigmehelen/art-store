package org.semicolon.semicolonartworksystem.dtos.responses;

import lombok.Data;
import org.semicolon.semicolonartworksystem.data.models.Cart;
import org.semicolon.semicolonartworksystem.data.models.Stock;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class CreateArtworkResponse {
    private String artworkId;
    private String artworkTitle;
    private String artworkDescription;
    private LocalDate datePainted;
    private Boolean isAvailable;
    private Stock stock;
    private BigDecimal price;
    private List<String> imageUrls;

}
