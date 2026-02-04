package org.semicolon.semicolonartworksystem.dtos.responses;

import lombok.Data;

@Data
public class AddToCartResponse {
    private String cartId;
    private String artworkId;
    private boolean success;
}
