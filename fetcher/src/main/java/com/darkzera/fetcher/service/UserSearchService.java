package com.darkzera.fetcher.service;

import com.darkzera.fetcher.entity.UserProfile;
import com.darkzera.fetcher.entity.dto.ArtistData;
import com.darkzera.fetcher.entity.dto.SearchArtistByNameDTO;
import com.darkzera.fetcher.repository.UserRepository;
import com.darkzera.fetcher.service.client.ImDBClient;
import com.darkzera.fetcher.service.client.SpotifyClient;
import com.darkzera.fetcher.service.client.SpotifyClientImplementation;
import com.sun.istack.NotNull;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;


@Component
public class UserSearchService {

    private SpotifyClient spotifyClient;
    private UserRepository userRepository;
    private UserAuthenticationService userAuthenticationService;
    public UserSearchService(SpotifyClientImplementation spotfyClientImplementation,
                             UserRepository userRepository,
                             UserAuthenticationService userAuthenticationService) {

        this.spotifyClient = spotfyClientImplementation;
        this.userRepository = userRepository;
        this.userAuthenticationService = userAuthenticationService;
    }


    public SearchArtistByNameDTO includeMusicArtistInProfileByName(@NotNull final String name) {

        List<String> header = Arrays.asList("spotify", "tidal", "lastfm");

        HashMap<String,SpotifyClient> services = new HashMap<>();
        services.put("spotify",spotifyClient);
        services.put("tidal", new ImDBClient());

        final SearchArtistByNameDTO searchResult = spotifyClient.findArtistByName(name);


        final UserProfile updatedProfile = attachArtistAndGenresToProfile(searchResult);

        return searchResult;

    }


    @Transactional
    private UserProfile attachArtistAndGenresToProfile(@NotNull final SearchArtistByNameDTO artistFound){

        final UserProfile userProf = userAuthenticationService.getUserProfile();

//        userProf.getMusicalArtists().add(artistFound.getFoundArtist().getName());
        userProf.addNewMusicalArtist(artistFound.getFoundArtist().getName());

//        userProf.getTop10Genres().addAll(artistFound.getGenres());
        userProf.addNewSetOfGenres(artistFound.getGenres());

        return userRepository.save(userProf);
    }

    public Object testDatabase(){
        return userRepository.show();
    }

}
