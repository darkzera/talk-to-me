package com.darkzera.fetcher.service.client;

import com.darkzera.fetcher.entity.dto.ArtistData;
import com.darkzera.fetcher.entity.dto.SearchArtistByNameDTO;
import com.darkzera.fetcher.service.client.provider.LastFMProvider;
import com.darkzera.fetcher.service.client.provider.MusicClient;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class LastFMClient implements MusicClient {

    private LastFMProvider lastFMProvider;
    private ReorganizeArtistData reorganizeArtistData;

    public LastFMClient(LastFMProvider lastFMProvider, ReorganizeArtistData reorganizeArtistData) {
        this.lastFMProvider = lastFMProvider;
        this.reorganizeArtistData = reorganizeArtistData;
    }

    @Override
    public SearchArtistByNameDTO findArtistByName(String name) {
        var rawResult = lastFMProvider.searchByName(name);
        var format = rawResult.stream()
                .map(ArtistData::fromLastfm)
                .collect(Collectors.toList());

        return reorganizeArtistData.rearrange(format, name);
    }


}
