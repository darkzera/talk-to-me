package com.darkzera.fetcher.service;

import com.darkzera.fetcher.config.MusicClientDispatcher;
import com.darkzera.fetcher.entity.UserProfile;
import com.darkzera.fetcher.entity.dto.SearchArtistByNameDTO;
import com.darkzera.fetcher.entity.enumerator.ClientProvider;
import com.darkzera.fetcher.repository.UserRepository;
import com.darkzera.fetcher.service.client.provider.MusicClient;
import com.sun.istack.NotNull;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;


@Component
public class UserSearchService {

    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(UserSearchService.class);
    private UserRepository userRepository;
    private UserAuthenticationService userAuthenticationService;
    private MusicClientDispatcher musicClientDispatcher;
    public UserSearchService(UserRepository userRepository,
                             UserAuthenticationService userAuthenticationService,
                             MusicClientDispatcher musicClientDispatcher) {

        this.userRepository = userRepository;
        this.userAuthenticationService = userAuthenticationService;
        this.musicClientDispatcher = musicClientDispatcher;
    }


    public UserProfile includeMusicArtistInProfileByName(@NotNull final String name) {

        var header = Arrays.asList(ClientProvider.SPOTIFYCLIENT, ClientProvider.LASTFMCLIENT);

        List<MusicClient> clients = musicClientDispatcher.doFilter(header);

        var rst = clients.stream().map(c -> c.findArtistByName(name)).collect(Collectors.toList());

        final UserProfile updatedProfile = attachArtistAndGenresToProfile(rst.get(0));

        return updatedProfile;

    }


    @Transactional
    private UserProfile attachArtistAndGenresToProfile(@NotNull final SearchArtistByNameDTO artistFound){

        final UserProfile userProf = userAuthenticationService.getUserProfile();

        userProf.addNewMusicalArtist(artistFound.getFoundArtist().getName());

        userProf.addNewSetOfGenres(artistFound.getGenres());

        return userRepository.save(userProf);
    }

    public Object testDatabase(){
        return userRepository.show();
    }

}
