package com.darkzera.fetcher.service.client.provider;

import org.apache.hc.core5.http.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.Artist;
import se.michaelthelin.spotify.model_objects.specification.Paging;

import java.io.IOException;

@Component
public class SpotifyProvider {
    private SpotifyApi spotifyApi;

    public SpotifyProvider(SpotifyApi spotifyApi) {
        this.spotifyApi = spotifyApi;
    }

    public Paging<Artist> searchArtists(final String artistName){
        try {
            Paging<Artist> rawResult = spotifyApi.searchArtists(artistName).build().execute();

            return rawResult;
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unknown", e);
        } catch (SpotifyWebApiException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to reach Spotify API", e);
        } catch (ParseException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to parse Spotify response", e);
        }

    }


}
