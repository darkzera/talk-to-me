package com.darkzera.fetcher.service;

import com.darkzera.fetcher.handler.DateHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IncludeAvailableSchedulerServiceTest {

    @InjectMocks
    private IncludeAvailableSchedulerService includeAvailableTimesService;

    @Mock
    private DateHandler dateHandler;

    @BeforeEach
    public void openMocks() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void includeAvailableDatesToSchedule() {

        List<LocalDateTime> datesToSchedule = Arrays.asList(
                LocalDateTime.of(2024,04,02,8,15),
                LocalDateTime.of(2024,04,03,8,15)
        );

        List<LocalDateTime> fromTodayOn = Arrays.asList(
                LocalDateTime.of(2024,04,02,8,15),
                LocalDateTime.of(2024,04,03,8,15),
                LocalDateTime.of(2024,01,03,5,15)
        );

        when(dateHandler.removeFromThePast(any())).thenReturn(datesToSchedule);

        includeAvailableTimesService.includeAvailableDatesToSchedule(datesToSchedule.stream().collect(Collectors.toSet()));


    }
}