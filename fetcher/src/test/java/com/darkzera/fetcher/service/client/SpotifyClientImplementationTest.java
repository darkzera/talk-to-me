package com.darkzera.fetcher.service.client;

import builder.ArtistDataBuilder;
import com.darkzera.fetcher.entity.dto.ArtistData;
import com.darkzera.fetcher.service.client.provider.SpotifyProvider;
import com.sun.istack.NotNull;
import org.apache.hc.core5.http.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.Artist;
import se.michaelthelin.spotify.model_objects.specification.Paging;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SpotifyClientImplementationTest {

    @InjectMocks
    private SpotifyClientImplementation spotifyClientImplementation;

    @Mock
    private SpotifyProvider spotifyProvider;
    @BeforeEach
    public void openMocks() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Given valid responde by Spotify API - it should sucessfully parse to domain model")
    void findArtistByName() throws IOException, ParseException, SpotifyWebApiException {
        // Considerando a implementacao interna a gente so captura os resultados da primeira pagina

        final String SEARCH_ARTIST_NAME = "Black Sabbath";

        Paging<Artist> artistPagingMock = ArtistDataBuilder.fromNames("Black Sabbath", "Heaven & Hell")
                .withGenresInOrder("Metal", "New Doom Metal Concept")
                .buildRestrictedPaging();

        when(spotifyProvider.searchArtists(SEARCH_ARTIST_NAME)).thenReturn(artistPagingMock);

        final List<ArtistData> actualResult = spotifyClientImplementation.findArtistByName(SEARCH_ARTIST_NAME);

        assertNotNull(actualResult);
        assertNotEmpty(actualResult);
        assertEquals(SEARCH_ARTIST_NAME, actualResult.get(0).getName());

    }

    private boolean assertNotEmpty(Collection cols){
        return cols.isEmpty();
    }

}