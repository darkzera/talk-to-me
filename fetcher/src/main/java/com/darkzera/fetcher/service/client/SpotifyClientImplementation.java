package com.darkzera.fetcher.service.client;

import com.darkzera.fetcher.service.client.provider.SpotifyProvider;
import com.darkzera.fetcher.entity.dto.ArtistData;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.model_objects.specification.Artist;
import se.michaelthelin.spotify.model_objects.specification.Paging;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class SpotifyClientImplementation implements SpotifyClient {

    private SpotifyProvider spotifyProvider;

    public SpotifyClientImplementation(SpotifyProvider spotifyProvider) {
        this.spotifyProvider = spotifyProvider;
    }

    @Override
    public List<ArtistData> findArtistByName(String artistName) {

        final Paging<Artist> artistPageable = spotifyProvider.searchArtists(artistName);
        final List<ArtistData> artistDataList = new ArrayList<>();

        for (Artist a : artistPageable.getItems()) {

            artistDataList.add(ArtistData.builder()
                    .name(a.getName())
                    .externalLink(a.getHref())
                    .genres(Arrays.stream(a.getGenres()).collect(Collectors.toSet()))
                    .build());
        }

        return artistDataList;

    }
}
