package org.semicolon.semicolonartworksystem.data.models;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Stock {
    @Id
    private String stockId;
    private String artworkId;
    private int quantity;

}
