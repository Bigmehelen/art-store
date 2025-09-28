package org.semicolon.semicolonartworksystem.data.repositories;

import org.semicolon.semicolonartworksystem.data.models.CartItem;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CartItems extends MongoRepository<CartItem, String> {
    List<CartItem> findByCartId(String cartId);
}
