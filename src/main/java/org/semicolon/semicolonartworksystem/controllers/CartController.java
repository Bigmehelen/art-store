package org.semicolon.semicolonartworksystem.controllers;

import org.semicolon.semicolonartworksystem.dtos.requests.AddToCartRequest;
import org.semicolon.semicolonartworksystem.dtos.responses.AddToCartResponse;
import org.semicolon.semicolonartworksystem.services.CartServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api-cart")
public class CartController {

    @Autowired
    private CartServices cartServices;

    @PostMapping("/add")
    public AddToCartResponse addToCart(@RequestBody AddToCartRequest request) {
        return cartServices.addToCart(request);
    }
}
