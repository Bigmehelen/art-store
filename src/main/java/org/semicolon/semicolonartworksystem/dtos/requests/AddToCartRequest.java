package org.semicolon.semicolonartworksystem.dtos.requests;

import lombok.Data;

@Data
public class AddToCartRequest {
    private String userId;
    private String artworkId;
}
