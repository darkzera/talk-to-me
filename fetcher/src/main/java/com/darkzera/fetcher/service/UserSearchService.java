package com.darkzera.fetcher.service;

import com.darkzera.fetcher.entity.UserProfile;
import com.darkzera.fetcher.entity.dto.ArtistData;
import com.darkzera.fetcher.entity.dto.SearchArtistByNameDTO;
import com.darkzera.fetcher.repository.UserRepository;
import com.darkzera.fetcher.service.client.SpotifyClientImplementation;
import com.sun.istack.NotNull;
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

    /* TODO
        Abstrair a conversao pro modelo de negocio dentro da
        Refatorar modelo de interface do Spotify. Nao tratar construcao de modelos na camada de business
        Acho que podemos criar um builder com algum parametro p identificar o tipo de construcao
            - Novo candidato p. modelo logico do negocio:
        {
            string searchName: Black Sabbath
            ARTIST_BUSINESSMODEL artistFound: Black Sabbath ->
            List<ARTIST_BUSINESSMODEL> sugestoes aplicáveis "black sabbath cover, black sabado, black tirico tico"
            generos
        }

     */
    public SearchArtistByNameDTO searchArtistByName(@NotNull String artistName){

        final var artists = (List<ArtistData>) spotifyClientImplementation.findArtistByName(artistName);
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

        userAuthenticationService.processUserProfile();
        final UserProfile updatedProfile = attachArtistToProfile(artist);

        userRepository.save(updatedProfile);

        return artist;

    }


    public UserProfile attachArtistToProfile(@NotNull final SearchArtistByNameDTO artistFound){

        UserProfile userProf = userRepository.findUserProfileByEmail(userAuthenticationService.getCurrentUserEmail())
                .orElseThrow(RuntimeException::new);

        userProf.getMusicalArtists().add(artistFound.getFoundArtist().getName());
        userProf.getTop10Genres().addAll(artistFound.getGenres());

        return userProf;
    }

    private List<ArtistData> extractOnlyExactlyMatches(@NotNull final List<ArtistData> artistListSource,
                                                       @NotNull final String artistName){
        /* TODO : remover ultimo arg. a nova versao do ArtistData carrega a tag de busca
            Além de fazer este filtro, posso considerar que os demais resultados sao sugestoes.
         */

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
