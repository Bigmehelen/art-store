package org.semicolon.semicolonartworksystem.data.repositories;

import org.semicolon.semicolonartworksystem.data.models.Stock;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface StockRepo extends MongoRepository<Stock,String> {
    Optional<Stock> findByArtworkId(String artworkId);
    Optional<Stock> findByStockId(String id);
}
