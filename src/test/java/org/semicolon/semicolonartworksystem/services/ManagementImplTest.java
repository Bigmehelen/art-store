package org.semicolon.semicolonartworksystem.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.semicolon.semicolonartworksystem.data.models.Artwork;
import org.semicolon.semicolonartworksystem.data.repositories.Artworks;
import org.semicolon.semicolonartworksystem.dtos.requests.CreateArtworkRequest;
import org.semicolon.semicolonartworksystem.dtos.responses.CreateArtworkResponse;
import org.semicolon.semicolonartworksystem.exceptions.ArtworkNotFoundException;
import org.semicolon.semicolonartworksystem.utils.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;


import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ManagementImplTest {
    private CreateArtworkRequest artworkRequest;
    private CreateArtworkRequest artworkRequestTwo;
    private CreateArtworkResponse artworkResponse;
    private CreateArtworkResponse artworkResponseTwo;

    @MockitoBean
    private Artworks artworks;

    @Autowired
    private Management management;

    @BeforeEach
    public void setup() {
        artworkRequest = new CreateArtworkRequest();
        artworkRequest.setArtworkTitle("Moremi");
        artworkRequest.setArtworkDescription("African Giant Artwork");
        artworkRequest.setDatePainted(LocalDate.parse("2018-04-04"));

        artworkRequestTwo = new CreateArtworkRequest();
        artworkRequestTwo.setArtworkTitle("jay");
        artworkRequestTwo.setArtworkDescription("This is jay");
        artworkRequestTwo.setDatePainted(LocalDate.parse("2018-08-09"));


        artworkResponse = new CreateArtworkResponse();
        artworkResponseTwo = new CreateArtworkResponse();
    }

    @Test
    public void testThatOneArtworkIsAdded() {
        Artwork artwork = Mapper.map(artworkRequest);
        when(artworks.save(any())).thenReturn(artwork);

        CreateArtworkResponse sent = management.addArtwork(artworkRequest);
        assertThat(sent).isNotNull();
        verify(artworks, times(1)).save(any());
        assertEquals(1L, management.inventory());
    }

    @Test
    public void testThatArtWorkIsAddedAndFoundById() {
        Artwork artwork = Mapper.map(artworkRequest);
        when(artworks.findById(any())).thenReturn(Optional.of(artwork));
        CreateArtworkResponse sent = management.findByArtworkId(artwork.getArtworkId());
        assertNotNull(sent);
    }

    @Test
    public void testThatCanDeleteArtworkWhenIdExists() {
        Artwork artwork = Mapper.map(artworkRequest);
        when(artworks.findById(any())).thenReturn(Optional.of(artwork));
        management.deleteByArtworkId(artwork.getArtworkId());
        verify(artworks, times(1)).deleteById(any());
        verify(artworks, times(1)).findById(any());
    }

    @Test
    public void testThatCanThrowExceptionWhenArtworkNotFound() {
        Artwork artwork = Mapper.map(artworkRequest);
        when(artworks.findById(any())).thenReturn(Optional.empty());
        assertThrowsExactly(ArtworkNotFoundException.class, ()-> management.findByArtworkId(artwork.getArtworkId()));
    }

    @Test
    public void testThatTwoArtworksAreAddedAndCanFindAll() {
        Artwork artwork = Mapper.map(artworkRequest);
        Artwork artworkTwo = Mapper.map(artworkRequestTwo);
        when(artworks.findAll()).thenReturn(Arrays.asList(artwork, artworkTwo));
        assertEquals(2L, management.findAllArtwork().size());
        CreateArtworkResponse sent = management.findAllArtwork().get(0);
        assertThat(sent).isNotNull();
        verify(artworks, times(2)).save(any());
    }

}