package com.darkzera.fetcher.service.client.provider;

import org.apache.hc.core5.http.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.ClientCredentials;
import se.michaelthelin.spotify.model_objects.specification.Artist;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;

import java.io.IOException;

@Component
public class SpotifyProvider {

    @Value("${spotify.clientId}")
    private String SPOTIFY_CLIENT_ID;
    @Value("${spotify.clientSecret}")
    private String SPOTIFY_CLIENT_SECRET;
    private SpotifyApi spotifyApi;
    private ClientCredentialsRequest clientCredentialsRequest;
    private ClientCredentials clientCredentials;

    public SpotifyProvider(SpotifyApi spotifyApi) {
        this.spotifyApi = spotifyApi;
    }

//    @PostConstruct
//    public void init(){
//        try {
//            spotifyApi = new SpotifyApi.Builder()
//                    .setClientId(SPOTIFY_CLIENT_ID)
//                    .setClientSecret(SPOTIFY_CLIENT_SECRET)
//                    .build();
//
//            clientCredentialsRequest =
//                    spotifyApi.clientCredentials().build();
//
//            clientCredentials = clientCredentialsRequest.execute();
//
//            spotifyApi.setAccessToken(clientCredentials.getAccessToken());
//
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        } catch (ParseException e) {
//            throw new RuntimeException(e);
//        } catch (SpotifyWebApiException e) {
//            throw new RuntimeException(e);
//        }
//    }

    public Paging<Artist> searchArtists(final String artistName){
        try {
            return spotifyApi.searchArtists(artistName).build().execute();
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unknown", e);
        } catch (SpotifyWebApiException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to reach Spotify API", e);
        } catch (ParseException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to parse Spotify response", e);
        }

    }


}
