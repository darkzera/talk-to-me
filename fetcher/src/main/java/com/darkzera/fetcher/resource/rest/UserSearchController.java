package com.darkzera.fetcher.resource.rest;

import com.darkzera.fetcher.service.UserSearchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class UserSearchController {

    private final UserSearchService userSearchService;

    public UserSearchController(UserSearchService userSearchService) {
        this.userSearchService = userSearchService;
    }


    @GetMapping("/artistByName/{name}")
    public ResponseEntity<?> fetchArtistByName(@PathVariable String name){
       var response = userSearchService.searchArtistByName(name);
       return ResponseEntity.ok(response);
    }

    @GetMapping("/")
    public ResponseEntity<?> just(){
        return ResponseEntity.ok(userSearchService.testDatabase());
    }


}
