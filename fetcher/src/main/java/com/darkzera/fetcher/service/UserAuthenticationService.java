package com.darkzera.fetcher.service;

import com.darkzera.fetcher.entity.UserProfile;
import com.darkzera.fetcher.entity.enumerator.AuthSupplier;
import com.darkzera.fetcher.repository.UserRepository;
import com.darkzera.fetcher.service.client.SpotifyClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public class UserAuthenticationService {


    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SpotifyClient spotifyClient;

    public Object getUserProfile_(){
        var user = SecurityContextHolder.getContext().getAuthentication().getDetails();
        return user;
    }
    public UserProfile getUserProfile(){

//        var principal = (DefaultOidcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        return userRepository.findUserProfileByEmail(principal.getEmail()).orElseThrow(() -> new RuntimeException());

        return processUserProfile();
    }
    @Deprecated
    public String getCurrentUserEmail() {
        return "fakeMailer@net.com";
    }

    // Enquanto a gente nao consegue montar o service especializado para processar isso aqui enquanto autentica o login
    // A cada vez que algum item for pesquisado p ser incluido é importante invocar o metodo abaixo
    // TODO: Verificar commit dc95cb4
    @Transactional
    @Deprecated
    public UserProfile processUserProfile(){

        if (userRepository.existsByEmail(getCurrentUserEmail())) {
            return userRepository.findUserProfileByEmail(getCurrentUserEmail())
                    .orElseThrow(RuntimeException::new);
        }

        var userProfile = new UserProfile();
        userProfile.setEmail("fakeMailer@net.com");
        userProfile.setAuthSupplier(AuthSupplier.GOOGLE);

        return userRepository.save(userProfile);
    }

    public Object t2(String param) {
        return spotifyClient.findArtistByName(param);
    }
}
