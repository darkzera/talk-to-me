package com.darkzera.fetcher.service;

import com.darkzera.fetcher.entity.UserProfile;
import com.darkzera.fetcher.handler.DateHandler;
import org.apache.hc.core5.http.HttpException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;


@Component
public class IncludeAvailableSchedulerService {

    private DateHandler dateHandler;
    private UserAuthenticationService authenticationService;

    public IncludeAvailableSchedulerService(DateHandler dateHandler, UserAuthenticationService authenticationService) {
        this.dateHandler = dateHandler;
        this.authenticationService = authenticationService;
    }

    public List<?> includeAvailableDatesToSchedule(List<LocalDateTime> availableDateTime){

//        final var user = (DefaultOidcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        final UserProfile requestUser = authenticationService.getUserProfile();

        final List<?> validDates = dateHandler.removeFromThePast(availableDateTime);

        if (validDates.isEmpty()){
            throw new RuntimeException("Nao fio");
        }







        return Collections.emptyList();
    }

}
