package com.darkzera.fetcher.handler;

import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.fasterxml.jackson.databind.type.LogicalType.DateTime;

@Component
public class DateHandler {

    private Clock clock;

    public DateHandler(Clock clock) {
        this.clock = clock;
    }

    public Collection<LocalDateTime> removeFromThePast(Collection<LocalDateTime> dates){

        return dates.stream()
                .filter(date -> date.isAfter(LocalDateTime.now(clock)))
                .collect(Collectors.toSet());

    }

}
