package org.semicolon.semicolonartworksystem.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.semicolon.semicolonartworksystem.data.models.Artwork;
import org.semicolon.semicolonartworksystem.data.models.Cart;
import org.semicolon.semicolonartworksystem.data.repositories.Artworks;
import org.semicolon.semicolonartworksystem.data.repositories.Carts;
import org.semicolon.semicolonartworksystem.dtos.requests.AddToCartRequest;
import org.semicolon.semicolonartworksystem.dtos.responses.AddToCartResponse;
import org.semicolon.semicolonartworksystem.utils.Mapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CartServicesImplTest {
    AddToCartRequest addToCart;
    AddToCartResponse response;

    @Mock
    private Artworks artworks;

    @Mock
    private Carts carts;

    @InjectMocks
    private CartServicesImpl cartServices;

    @BeforeEach
    public void setUp() {
        addToCart = new AddToCartRequest();
        addToCart.setArtworkId("1");
        addToCart.setUserId("2");

        response = new AddToCartResponse();
    }

    @Test
    public void testThatItemsCanBeAddedToCart() {
        Artwork artwork = new Artwork();
        Cart cart = Mapper.mapToModel(addToCart);
        when(artworks.findById(any())).thenReturn(Optional.of(artwork));
        when(carts.findByUserId(any())).thenReturn(Optional.of(cart));
        when(carts.save(any())).thenAnswer(i -> i.getArgument(0));

        AddToCartResponse sent = cartServices.addToCart(addToCart);
        assertNotNull(sent);
    }

}
