package com.darkzera.fetcher.config;

import org.apache.hc.core5.http.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.ClientCredentials;
import se.michaelthelin.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;

import java.io.IOException;

@Configuration
public class SpotifyClientConfig{

    @Value("${spotify.clientId}")
    private String SPOTIFY_CLIENT_ID;
    @Value("${spotify.clientSecret}")
    private String SPOTIFY_CLIENT_SECRET;
    private SpotifyApi spotifyApi;
    private ClientCredentialsRequest clientCredentialsRequest;
    private ClientCredentials clientCredentials;

    @Bean
    public SpotifyApi spotifyApi() {
        try {
            spotifyApi = new SpotifyApi.Builder()
                    .setClientId(SPOTIFY_CLIENT_ID)
                    .setClientSecret(SPOTIFY_CLIENT_SECRET)
                    .build();

            clientCredentialsRequest =
                    spotifyApi.clientCredentials().build();

            clientCredentials = clientCredentialsRequest.execute();

            spotifyApi.setAccessToken(clientCredentials.getAccessToken());

            return spotifyApi;

        } catch (
                IOException e) {
            throw new RuntimeException(e);
        } catch (
                ParseException e) {
            throw new RuntimeException(e);
        } catch (
                SpotifyWebApiException e) {
            throw new RuntimeException(e);
        }
    }
}
