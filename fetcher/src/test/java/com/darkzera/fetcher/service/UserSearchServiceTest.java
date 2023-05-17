package com.darkzera.fetcher.service;

import builder.ArtistDataBuilder;
import com.darkzera.fetcher.service.client.provider.SpotifyProvider;
import com.darkzera.fetcher.entity.UserProfile;
import com.darkzera.fetcher.entity.dto.ArtistData;
import com.darkzera.fetcher.entity.enumerator.AuthSupplier;
import com.darkzera.fetcher.repository.UserRepository;
import com.darkzera.fetcher.service.client.SpotifyClientImplementation;
import com.darkzera.fetcher.service.exception.UserCannotBeFoundInDatabase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Spotify user include artist to profile - Include spotify artist to user profile - use case service")
public class UserSearchServiceTest {


    @InjectMocks
    private UserSearchService userSearchService;

    @Mock
    private UserRepository userRepository;


    @Mock
    private SpotifyProvider spotifyProvider;

    @Mock
    private SpotifyClientImplementation spotifyClientImplementation;

    @Mock
    private UserAuthenticationService userAuthenticationService;


    @BeforeEach
    public void openMocks() {
        MockitoAnnotations.openMocks(this);
    }

    private UserProfile buildDefaultUserProfile(){
//   Percebi que nao da pra usar Sets.of(...) pra injetar (-mockar) uma collection q sera alterada dentro da service
        Set<String> presentMusicalArtist = new HashSet<>();
        presentMusicalArtist.add("Parangole");
        Set<String> presentGenres = new HashSet<>();
        presentGenres.add("Arrocha?");

        UserProfile userProfile = new UserProfile();
        userProfile.setEmail("teste@mail.net");
        userProfile.setId(1L);
        userProfile.setAuthSupplier(AuthSupplier.GOOGLE);
        userProfile.setMusicalArtists(presentMusicalArtist);
        userProfile.setTop10Genres(presentGenres);
        return userProfile;
    }
    @Test
    @DisplayName("Given valid band/artist name it shuold sucessfully it with applied rules and return as our business model")
    public void t1() {
        final String SEARCH_NAME = "Sator Arepo";

        List<ArtistData> artistFakeList_ = ArtistDataBuilder
                .fromNames("Metallica", "Sator Arepo", "Sator Arepo Tenet Opera")
                .withGenresInOrder("Thrash Metal", "Darkzera", "AluciDommed")
                .buildRestrictedList();

        var userProfile = buildDefaultUserProfile();


//        when(spotifyClientImplementation.findArtistByName(anyString())).thenReturn(artistFakeList_);

        when(userAuthenticationService.getUserProfile()).thenReturn(userProfile);

        when(userRepository.save(any())).thenReturn(userProfile);

        var actualResult = userSearchService.includeMusicArtistInProfileByName("Sator Arepo");

        assertNotNull(actualResult);

        assertNotEquals("Suggestions shuld not be empty", 0, actualResult.getSuggestions().size());

        assertTrue("We must have something similar to principal search name",
                actualResult.getSuggestions().stream().anyMatch(a -> a.getName().equals("Sator Arepo Tenet Opera")));

        assertEquals("Principal artist name shuold match to given input",
                SEARCH_NAME, actualResult.getFoundArtist().getName());
    }

    @Test
    @DisplayName("Given valid input it shuold fail to find user by email and throw customized exception")
    public void t3(){
        final String SEARCH_NAME = "Sator Arepo";

        List<ArtistData> artistFakeList_ = ArtistDataBuilder
                .fromNames("Metallica", "Sator Arepo", "Sator Arepo Tenet Opera")
                .withGenresInOrder("Thrash Metal", "Darkzera", "AluciDommed")
                .buildRestrictedList();

        var userProfile = buildDefaultUserProfile();


//        when(spotifyClientImplementation.findArtistByName(anyString())).thenReturn(artistFakeList_);

        when(userAuthenticationService.getCurrentUserEmail()).thenReturn(userProfile.getEmail());

        when(userRepository.findUserProfileByEmail(anyString())).thenThrow(UserCannotBeFoundInDatabase.class);
        when(userRepository.save(any())).thenReturn(userProfile);


    }

    @Test
    @DisplayName("Given what we consider invalid band/artist name - it shuold ONLY fetch suggested itens")
    @Disabled
    public void t2() {
        final String SEARCH_NAME = "aushdaushea";

        List<ArtistData> artistFakeList_ = ArtistDataBuilder
                .fromNames("Ashley", "OUTSHADES", "Aisha")
                .withGenresInOrder("Pop", "Popundertake", "Reggae")
                .buildRestrictedList();

        var userProfile = buildDefaultUserProfile();


//        when(spotifyClientImplementation.findArtistByName(anyString())).thenReturn(artistFakeList_);

        when(userAuthenticationService.getCurrentUserEmail()).thenReturn(userProfile.getEmail());

        when(userRepository.findUserProfileByEmail(anyString())).thenReturn(Optional.of(userProfile));
        when(userRepository.save(any())).thenReturn(userProfile);

        var actualResult = userSearchService.includeMusicArtistInProfileByName(SEARCH_NAME);
        assertNotNull(actualResult);

        assertNotEquals("Suggestions shuld not be empty", 0, actualResult.getSuggestions().size());

//        assertTrue("We must have something similar to principal search name",
//                actualResult.getSuggestions().stream().anyMatch(a -> a.getName().equals("Sator Arepo Tenet Opera")));
//
//        assertEquals("Principal artist name shuold match to given input",
//                SEARCH_NAME, actualResult.getFoundArtist().getName());
    }
}