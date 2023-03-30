package com.darkzera.fetcher.service.client;

import com.darkzera.fetcher.config.SpotifyProvider;
import com.darkzera.fetcher.entity.dto.ArtistData;
import org.apache.hc.core5.http.ParseException;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.Artist;
import se.michaelthelin.spotify.model_objects.specification.Paging;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class SpotifyClientImplementation implements SpotifyClient {

    private final SpotifyApi spotifyApi;

    public SpotifyClientImplementation(SpotifyProvider spotifyProvider) {
        spotifyProvider.executeCredentials();
        spotifyApi = spotifyProvider.getSpotifyApi();
    }

    @Override
    public List<ArtistData> findArtistByName(String artistName) {

        Paging<Artist> artistPageable = null;

        try {
            artistPageable = spotifyApi.searchArtists(artistName).build().execute();
            List<ArtistData> artistDataList = new ArrayList<>();

        /*
                We can use a direct request using queryParam and get N itens at once
                Or we can fancy implement a interface to propely deal with library Paging result
                Also could be great contribute to this project opensource to improove the lib
                For now we are just fetching only few results
        */

            for (Artist a : artistPageable.getItems()) {

                artistDataList.add(ArtistData.builder()
                        .name(a.getName())
                        .externalLink(a.getHref())
                        .genres(Arrays.stream(a.getGenres()).collect(Collectors.toSet()))
                        .build());
            }

            return artistDataList;

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SpotifyWebApiException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

    }
}
