package org.semicolon.semicolonartworksystem.utils;

import lombok.Data;
import org.semicolon.semicolonartworksystem.data.models.Artwork;
import org.semicolon.semicolonartworksystem.data.models.Cart;
import org.semicolon.semicolonartworksystem.data.models.User;
import org.semicolon.semicolonartworksystem.dtos.requests.AddToCartRequest;
import org.semicolon.semicolonartworksystem.dtos.requests.CreateArtworkRequest;
import org.semicolon.semicolonartworksystem.dtos.requests.LoginRequest;
import org.semicolon.semicolonartworksystem.dtos.requests.SignUpRequest;
import org.semicolon.semicolonartworksystem.dtos.responses.AddToCartResponse;
import org.semicolon.semicolonartworksystem.dtos.responses.CreateArtworkResponse;
import org.semicolon.semicolonartworksystem.dtos.responses.LoginResponse;
import org.semicolon.semicolonartworksystem.dtos.responses.SignUpResponse;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;


@Data
public class Mapper {

    @Value("${IMAGE_BASE_URL}")
    private String baseUrl;


    public static Artwork map(CreateArtworkRequest artworkRequest) {
        Artwork artwork = new Artwork();
        artwork.setArtworkTitle(artworkRequest.getArtworkTitle());
        artwork.setArtworkDescription(artworkRequest.getArtworkDescription());
        artwork.setDatePainted(artworkRequest.getDatePainted());
        artwork.setIsAvailable(artworkRequest.getIsAvailable());
        artwork.setPrice(artworkRequest.getPrice());
        List<String> filenames = artworkRequest.getFilenames();
        List<String> imageUrls = filenames.stream()
                .map(filename-> "http://localhost:2000/api/v1/artwork/download/"+filename)
                .toList();
        artwork.setImageUrls(imageUrls);
        return artwork;
    }

    public static CreateArtworkResponse map(Artwork entity){
        CreateArtworkResponse response = new CreateArtworkResponse();
        response.setArtworkId(entity.getArtworkId());
        response.setArtworkTitle(entity.getArtworkTitle());
        response.setArtworkDescription(entity.getArtworkDescription());
        response.setDatePainted(entity.getDatePainted());
        response.setIsAvailable(entity.getIsAvailable());
        response.setStock(entity.getStock());
        response.setPrice(entity.getPrice());
        response.setImageUrls(entity.getImageUrls());
        return response;
    }

    public static User map(LoginRequest request) {
        User entity = new User();
        entity.setEmail(request.getEmail());
        entity.setPassword(request.getPassword());
        return entity;
    }

    public static LoginResponse map(User user) {
        LoginResponse saved = new LoginResponse();
        saved.setToken(user.getToken());
        return saved;
    }

    public static User mapToModel(SignUpRequest request){
        User users = new User();
        users.setUserName(request.getUserName());
        users.setEmail(request.getEmail());
        users.setPassword(request.getPassword());
        return users;
    }

    public static SignUpResponse mapToEntity(User entity){
        SignUpResponse responses = new SignUpResponse();
        responses.setToken(entity.getToken());
        return responses;
    }

    public static Cart mapToModel(AddToCartRequest request){
        Cart cart = new Cart();
        cart.setUserId(request.getUserId());
        cart.setArtworkId(request.getArtworkId());
        return cart;
    }

    public static AddToCartResponse mapToResponse(Cart entity ){
        AddToCartResponse response = new AddToCartResponse();
        response.setCartId(entity.getCartId());
        response.setSuccess(true);
        return response;
    }
}
