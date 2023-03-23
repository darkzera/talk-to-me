package com.darkzera.fetcher.service;

import com.darkzera.fetcher.repository.UserRepository;
import com.darkzera.fetcher.service.client.SpotifyClient;
import com.darkzera.fetcher.service.client.SpotifyClientImplementation;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.model_objects.specification.Artist;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class UserSearchService {

    private final SpotifyClient spotifyClient;
    private final UserRepository userRepository;

    public UserSearchService(SpotifyClientImplementation spotfyClientImplementation, UserRepository userRepository) {
        this.spotifyClient = spotfyClientImplementation;
        this.userRepository = userRepository;
    }

    public List<?> searchArtistByName(String name){
        var list = spotifyClient.findArtistByName(name);
        List<Artist> artists = (List<Artist>) list;

        HashMap<Artist, List<String>> genres = new HashMap<>();

        for (Artist a : artists) {
           genres.put(a, Arrays.stream(a.getGenres()).collect(Collectors.toList()));
        }

        return (List<?>) list;
    }

    public Object testDatabase(){
       return userRepository.show();
    }

}
