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
        var t = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        var response = userSearchService.includeMusicArtistInProfileByName(name);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/")
    public ResponseEntity<?> just(){
        OAuth2User user = getCurrentUser();
        StringBuffer authorities = new StringBuffer();
        user.getAuthorities().forEach((a) -> authorities.append(a.toString()).append(","));
        return ResponseEntity.ok(
                "Hello " + user.getAttributes().get("name") + ". Your email is " + user.getAttributes().get("email")
                + " and your profile picture is <img src='"+user.getAttributes().get("picture")+"' /> <br />"
                + "You have the following attributes: " + authorities.toString() + "<br />"
                + "<a href='/logout'>logout</a>");
    }


    public OAuth2User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return ((OAuth2AuthenticationToken)auth).getPrincipal();
    }
}
