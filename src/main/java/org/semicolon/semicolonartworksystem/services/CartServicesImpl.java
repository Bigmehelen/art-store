package org.semicolon.semicolonartworksystem.services;

import lombok.extern.slf4j.Slf4j;
import org.semicolon.semicolonartworksystem.data.models.Artwork;
import org.semicolon.semicolonartworksystem.data.models.Cart;
import org.semicolon.semicolonartworksystem.data.repositories.Artworks;
import org.semicolon.semicolonartworksystem.data.repositories.Carts;
import org.semicolon.semicolonartworksystem.dtos.requests.AddToCartRequest;
import org.semicolon.semicolonartworksystem.dtos.responses.AddToCartResponse;
import org.semicolon.semicolonartworksystem.exceptions.ArtworkNotFoundException;
import org.semicolon.semicolonartworksystem.exceptions.UserNotFoundException;
import org.semicolon.semicolonartworksystem.utils.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
public class CartServicesImpl implements CartServices {
    @Autowired
    private Artworks artworks;

    @Autowired
    private Carts carts;

    @Override
    public AddToCartResponse addToCart(AddToCartRequest request) {
        Artwork artwork = artworks.findById(request.getArtworkId())
                .orElseThrow(() -> new ArtworkNotFoundException(request.getArtworkId()));

        Cart cart = carts.findByUserId(request.getUserId())
                .orElseThrow(() -> new UserNotFoundException(request.getUserId()));
        log.info("Adding to cart {} ", request.getUserId());
        cart.getAllArts().add(artwork);
        Cart saved = carts.save(cart);

        return Mapper.mapToResponse(saved);
    }

}
