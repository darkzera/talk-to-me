package com.darkzera.fetcher.resource.rest;

import com.darkzera.fetcher.service.IncludeAvailableSchedulerService;
import com.darkzera.fetcher.service.UserAuthenticationService;
import com.darkzera.fetcher.service.UserSearchService;
import org.apache.hc.core5.http.HttpException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

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
    public ResponseEntity<?> includeMusicArtistByName(@PathVariable String name,
                                                      @RequestHeader Map<String, String> headers){
        final var negotiation = Optional.of(headers.get("service")).orElseThrow(RuntimeException::new);
        var response = userSearchService.includeMusicArtistInProfileByName(name);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/schedule")
    public Object includeSchedule(@RequestHeader Map<String, String> headers,
                                  @RequestBody Set<LocalDateTime> available){
        return includeAvailableSchedulerService.includeAvailableDatesToSchedule(available);
    }
    @PostMapping("/schedule2")
    public Object includeSchedule(@RequestBody LocalDateTime available){
        return includeAvailableSchedulerService.query(available);
    }
    @GetMapping("/profile")
    public ResponseEntity<?> profile(){
        return ResponseEntity.ok(userAuthenticationService.getUserProfile_());
    }
}
