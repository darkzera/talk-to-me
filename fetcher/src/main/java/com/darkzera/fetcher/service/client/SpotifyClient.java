package com.darkzera.fetcher.service.client;

import com.darkzera.fetcher.entity.dto.SearchArtistByNameDTO;

public interface SpotifyClient {


    SearchArtistByNameDTO findArtistByName(String artistName);


}
