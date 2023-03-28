package com.darkzera.fetcher.resource.rest;

import com.darkzera.fetcher.service.UserAuthenticationService;
import com.darkzera.fetcher.service.UserSearchService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class UserSearchController {

    private UserSearchService userSearchService;
    private UserAuthenticationService userAuthenticationService;

    public UserSearchController(UserSearchService userSearchService, UserAuthenticationService userAuthenticationService) {
        this.userSearchService = userSearchService;
        this.userAuthenticationService = userAuthenticationService;
    }


    @GetMapping("/includeMusicArtist/{name}")
    public ResponseEntity<?> includeMusicArtistByName(@PathVariable String name){

        var response = userSearchService.includeMusicArtistInProfileByName(name);

        return ResponseEntity.ok(response);
    }
    @GetMapping("/testDB")
    public ResponseEntity<?> verifyDBTable(){
        return ResponseEntity.ok(userSearchService.testDatabase());
    }

    @GetMapping("/userInfo")
    public ResponseEntity<?> getUserAbout(){
       return ResponseEntity.ok(userAuthenticationService.processUserProfile());
    }


}
