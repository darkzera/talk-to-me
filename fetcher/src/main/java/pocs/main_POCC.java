package pocs;

import lombok.SneakyThrows;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.ClientCredentials;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.Track;
import se.michaelthelin.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;
import se.michaelthelin.spotify.requests.data.browse.miscellaneous.GetAvailableGenreSeedsRequest;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.text.ParseException;
import java.util.Arrays;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

public class main_POCC {
    private static final String accessToken =
            "BQDyNmOgtSHyH9VeWgmVvW1uCmgmyEZ5znvpMDfzqQZIfI5h9jPen-CSPucAY4_PzNOHqJKSJPlOta1qPZmMOQ_gIUDREN7bnDixx-GW72mN24BMb5fV";

    private static final SpotifyApi spotifyApi = new SpotifyApi.Builder()
            .setAccessToken(accessToken)
            .build();
    private static final GetAvailableGenreSeedsRequest getAvailableGenreSeedsRequest = spotifyApi.getAvailableGenreSeeds()
            .build();

    public static void getAvailableGenreSeeds_Sync() {
        try {
            final String[] strings = getAvailableGenreSeedsRequest.execute();

            Arrays.stream(strings).forEach(System.out::println);
            System.out.println("Length: " + strings.length);
        } catch (IOException | SpotifyWebApiException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (org.apache.hc.core5.http.ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static void getAvailableGenreSeeds_Async() {
        try {
            final CompletableFuture<String[]> stringsFuture = getAvailableGenreSeedsRequest.executeAsync();

            // Thread free to do other tasks...

            // Example Only. Never block in production code.
            final String[] strings = stringsFuture.join();

            System.out.println("Length: " + strings.length);
        } catch (CompletionException e) {
            System.out.println("Error: " + e.getCause().getMessage());
        } catch (CancellationException e) {
            System.out.println("Async operation cancelled.");
        }
    }

    public static void main(String[] args) {
        getAvailableGenreSeeds_Sync();
        getAvailableGenreSeeds_Async();
    }
}