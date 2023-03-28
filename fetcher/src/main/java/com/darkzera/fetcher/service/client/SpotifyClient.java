package com.darkzera.fetcher.service.client;

import com.darkzera.fetcher.entity.dto.ArtistData;

import java.util.List;

public interface SpotifyClient {


    List<ArtistData> findArtistByName(String artistName);


}
