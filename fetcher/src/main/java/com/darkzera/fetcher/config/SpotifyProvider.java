package com.darkzera.fetcher.config;

import lombok.Getter;
import org.apache.hc.core5.http.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.ClientCredentials;
import se.michaelthelin.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;

import java.io.IOException;

@Component
public class SpotifyProvider {

    @Value("${spotify.clientId}")
    public String SPOTIFY_CLIENT_ID;
    @Value("${spotify.clientSecret}")
    public String SPOTIFY_CLIENT_SECRET;
    @Getter
    public SpotifyApi spotifyApi;
    public ClientCredentialsRequest clientCredentialsRequest;
    public ClientCredentials clientCredentials;

    public void executeCredentials(){
        try {
            spotifyApi = new SpotifyApi.Builder()
                    .setClientId(SPOTIFY_CLIENT_ID)
                    .setClientSecret(SPOTIFY_CLIENT_SECRET)
                    .build();

            clientCredentialsRequest =
                    spotifyApi.clientCredentials().build();

            clientCredentials = clientCredentialsRequest.execute();

            spotifyApi.setAccessToken(clientCredentials.getAccessToken());

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SpotifyWebApiException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }


}
