package com.darkzera.fetcher.service;

import com.darkzera.fetcher.entity.UserProfile;
import com.darkzera.fetcher.handler.DateHandler;
import com.darkzera.fetcher.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Set;


@Component
public class IncludeAvailableSchedulerService {

    private DateHandler dateHandler;
    private UserAuthenticationService authenticationService;
    private UserRepository userRepository;

    public IncludeAvailableSchedulerService(DateHandler dateHandler,
                                            UserAuthenticationService authenticationService,
                                            UserRepository userRepository) {
        this.dateHandler = dateHandler;
        this.authenticationService = authenticationService;
        this.userRepository = userRepository;
    }

    public UserProfile includeAvailableDatesToSchedule(Set<LocalDateTime> availableDateTime){

        final UserProfile requestUser = authenticationService.getUserProfile();
        userRepository.disponivel(requestUser.getEmail());

        final var validDates = dateHandler.removeFromThePast(availableDateTime);

        if (validDates.isEmpty()){
            throw new RuntimeException("Nao fio");
        }

        requestUser.includeAvailableTimes((Set<LocalDateTime>) validDates);



        return userRepository.save(requestUser);
    }

    public Object query(LocalDateTime available) {
        return userRepository.findDatesAfter(available);
    }
}
