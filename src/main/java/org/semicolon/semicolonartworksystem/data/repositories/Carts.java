package org.semicolon.semicolonartworksystem.data.repositories;

import org.semicolon.semicolonartworksystem.data.models.Cart;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface Carts extends MongoRepository<Cart, String> {
    Optional<Cart> findByUserId(String userId);

}
