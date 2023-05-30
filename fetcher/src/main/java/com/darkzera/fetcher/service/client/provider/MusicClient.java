package com.darkzera.fetcher.service.client.provider;

import com.darkzera.fetcher.entity.dto.ArtistData;
import com.darkzera.fetcher.entity.dto.SearchArtistByNameDTO;

import java.util.Collection;

public interface MusicClient {

    SearchArtistByNameDTO findArtistByName(String name);
}
