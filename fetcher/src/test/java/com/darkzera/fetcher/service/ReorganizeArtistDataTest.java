package com.darkzera.fetcher.service;

import builder.ArtistDataBuilder;
import com.darkzera.fetcher.entity.dto.ArtistData;
import com.darkzera.fetcher.entity.dto.SearchArtistByNameDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ReorganizeArtistDataTest {

    @InjectMocks
    private ReorganizeArtistData reorganizeArtistData;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);
    }


    @Test
    void t1(){
        List<ArtistData> rawSpotifyResult = Arrays.asList(
                ArtistData.builder().name("Black Sabbath").genres(Set.of("Metal", "Grind metal")).build(),
                ArtistData.builder().name("Black Sabbath Reborno").genres(Set.of("Comics metal", "Grind metal", "wtf")).build(),
                ArtistData.builder().name("Black Sabbathore").genres(Set.of("cover", "Grind metal", "wtf")).build(),
                ArtistData.builder().name("Black Sabadao").genres(Set.of()).build()
        );

        final SearchArtistByNameDTO expected = SearchArtistByNameDTO.builder()
                .foundArtist(ArtistData.builder().name("Black Sabbath").genres(Set.of("Metal", "Grind metal")).build())
                .genres(Set.of("Metal", "Grind Metal"))
                .suggestions(Arrays.asList(
                        ArtistData.builder().name("Black Sabbath Reborno").genres(Set.of("Comics metal", "Grind metal", "wtf")).build(),
                        ArtistData.builder().name("Black Sabbathore").genres(Set.of("cover", "Grind metal", "wtf")).build()))
                .queryName("Black Sabbath")
                .build();

        var actual = reorganizeArtistData.rearrange(rawSpotifyResult, "Black Sabbath");



        assertNotNull(actual);

        assertEquals(expected.getFoundArtist(), actual.getFoundArtist());



    }
}