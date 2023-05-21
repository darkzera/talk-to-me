package com.darkzera.fetcher.resource.rest;

import com.darkzera.fetcher.service.IncludeAvailableSchedulerService;
import com.darkzera.fetcher.service.UserAuthenticationService;
import com.darkzera.fetcher.service.UserSearchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping
public class UserSearchController {

    private UserSearchService userSearchService;
    private UserAuthenticationService userAuthenticationService;
    private IncludeAvailableSchedulerService includeAvailableSchedulerService;

    public UserSearchController(UserSearchService userSearchService,
                                UserAuthenticationService userAuthenticationService,
                                IncludeAvailableSchedulerService includeAvailableSchedulerService) {
        this.userSearchService = userSearchService;
        this.userAuthenticationService = userAuthenticationService;
        this.includeAvailableSchedulerService = includeAvailableSchedulerService;
    }


    @GetMapping("/includeMusicArtist/{name}")
    public ResponseEntity<?> includeMusicArtistByName(@PathVariable String name){
        var response = userSearchService.includeMusicArtistInProfileByName(name);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/schedule")
    public Object includeSchedule(@RequestBody List<LocalDateTime> available){
        return includeAvailableSchedulerService.includeAvailableDatesToSchedule(available);
    }

    @GetMapping("/profile")
    public ResponseEntity<?> profile(){
        return ResponseEntity.ok(userAuthenticationService.getUserProfile());
    }
}
