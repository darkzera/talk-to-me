package com.darkzera.fetcher.service;

import com.darkzera.fetcher.service.client.SpotifyClient;
import com.darkzera.fetcher.service.client.SpotifyClientImplementation;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserSearchService {

    private final SpotifyClient spotifyClient;

    public UserSearchService(SpotifyClientImplementation spotfyClientImplementation) {
        this.spotifyClient = spotfyClientImplementation;
    }

    public List<?> searchArtistByName(String name){
        var list = spotifyClient.findArtistByName(name);

        return (List<?>) list;
    }
}
