package org.semicolon.semicolonartworksystem.data.models;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
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
