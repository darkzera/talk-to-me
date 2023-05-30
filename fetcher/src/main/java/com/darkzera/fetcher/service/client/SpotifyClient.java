package com.darkzera.fetcher.service.client;

import com.darkzera.fetcher.entity.dto.SearchArtistByNameDTO;
import com.darkzera.fetcher.service.client.provider.MusicClient;
import com.darkzera.fetcher.service.client.provider.SpotifyProvider;
import com.darkzera.fetcher.entity.dto.ArtistData;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
public class SpotifyClient implements MusicClient {

    private SpotifyProvider spotifyProvider;

    private ReorganizeArtistData reorganizeArtistData;

    public SpotifyClient(SpotifyProvider spotifyProvider,
                         ReorganizeArtistData reorganizeArtistData) {
        this.spotifyProvider = spotifyProvider;
        this.reorganizeArtistData = reorganizeArtistData;
    }

    @Override
    public SearchArtistByNameDTO findArtistByName(String artistName) {
        final var artistPageable = spotifyProvider.searchArtists(artistName);

        final var artistData = Stream.of(artistPageable.getItems())
                .map(ArtistData::fromSpotify)
                .collect(Collectors.toList());

        return reorganizeArtistData.rearrange(artistData, artistName);
    }
}
