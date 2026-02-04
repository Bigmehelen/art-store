package org.semicolon.semicolonartworksystem.data.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.HashSet;
import java.util.Set;

@Data
@Document(collection = "carts")
public class Cart {
    @Id
    private String cartId;
    private String artworkId;
    private String userId;

    private Set<Artwork> allArts = new HashSet<>();
}
