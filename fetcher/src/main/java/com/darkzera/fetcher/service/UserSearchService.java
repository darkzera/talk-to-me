package com.darkzera.fetcher.service;

import com.darkzera.fetcher.entity.UserProfile;
import com.darkzera.fetcher.entity.dto.ArtistData;
import com.darkzera.fetcher.entity.dto.SearchArtistByNameDTO;
import com.darkzera.fetcher.repository.UserRepository;
import com.darkzera.fetcher.service.client.SpotifyClientImplementation;
import com.darkzera.fetcher.service.exception.UserCannotBeFoundInDatabase;
import com.sun.istack.NotNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import se.michaelthelin.spotify.model_objects.specification.Artist;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;


@Component
public class UserSearchService {

    private SpotifyClientImplementation spotifyClientImplementation;
    private UserRepository userRepository;
    private UserAuthenticationService userAuthenticationService;
    public UserSearchService(SpotifyClientImplementation spotfyClientImplementation,
                             UserRepository userRepository,
                             UserAuthenticationService userAuthenticationService) {

        this.spotifyClientImplementation = spotfyClientImplementation;
        this.userRepository = userRepository;
        this.userAuthenticationService = userAuthenticationService;
    }

    public SearchArtistByNameDTO searchArtistByName(@NotNull String artistName){

        final List<ArtistData>artists = spotifyClientImplementation.findArtistByName(artistName);

        final List<ArtistData> artistDataList = extractOnlyExactlyMatches(artists, artistName);

        final ArtistData principalArtist = artistDataList.stream()
                .filter(art -> art.getName().equalsIgnoreCase(artistName))
                .findFirst().orElseThrow();

        artistDataList.remove(principalArtist);

        final Set<String> genres = extractGenresForEntireCollection(artistDataList);

        return SearchArtistByNameDTO.builder()
                .foundArtist(principalArtist)
                .suggestions(artistDataList)
                .genres(genres)
                .queryName(artistName)
                .build();
    }

    @Transactional
    public SearchArtistByNameDTO includeMusicArtistInProfileByName(@NotNull final String name) {

        final SearchArtistByNameDTO artist = searchArtistByName(name);

        final UserProfile updatedProfile = attachArtistAndGenresToProfile(artist);

        userRepository.save(updatedProfile);

        return artist;

    }


    private UserProfile attachArtistAndGenresToProfile(@NotNull final SearchArtistByNameDTO artistFound){

        final UserProfile userProf = userRepository
                .findUserProfileByEmail(userAuthenticationService.getCurrentUserEmail())
                .orElseThrow(UserCannotBeFoundInDatabase::new);

        userProf.getMusicalArtists().add(artistFound.getFoundArtist().getName());
        userProf.getTop10Genres().addAll(artistFound.getGenres());

        return userProf;
    }

    private List<ArtistData> extractOnlyExactlyMatches(@NotNull final List<ArtistData> artistListSource,
                                                       @NotNull final String artistName){

        return artistListSource.stream()
                .filter(artist -> artist.getName().contains(artistName))
                .collect(Collectors.toList());
    }

    private Set<String> extractGenresForEntireCollection(List<ArtistData> artistDataList){
        var temp = artistDataList.stream()
                .map(ArtistData::getGenres)
                .map(Set::toString)
                .collect(Collectors.toSet());

        return temp;
    }

    public Object testDatabase(){
        return userRepository.show();
    }

}
