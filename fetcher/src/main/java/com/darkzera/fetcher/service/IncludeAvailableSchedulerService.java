package com.darkzera.fetcher.service;

import com.darkzera.fetcher.handler.DateHandler;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;


@Component
public class IncludeAvailableSchedulerService {

    private DateHandler dateHandler;

    public IncludeAvailableSchedulerService(DateHandler dateHandler) {
        this.dateHandler = dateHandler;
    }

    public List<?> includeAvailableDatesToSchedule(List<LocalDateTime> availableDateTime){

       final List<?> t = dateHandler.removeFromThePast(availableDateTime);


       return t;
    }

}
