package com.darkzera.fetcher.handler;

import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.commons.logging.LoggerFactory;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DateHandlerTest {

    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(DateHandlerTest.class);

    @InjectMocks
    private DateHandler dateHandler;

    @Mock
    private Clock clock;

    private static ZonedDateTime NOW = ZonedDateTime.of(
            2024, 04, 06,
            14, 30,
            30, 30,
            ZoneId.of("GMT")
    );

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        when(clock.getZone()).thenReturn(NOW.getZone());
        when(clock.instant()).thenReturn(NOW.toInstant());
    }

    @Test
    @DisplayName("Given a collection of dates, it should propely remove dates from the past and return today -> future")
    void removeFromThePast() {

        System.out.println(LocalDateTime.now());
        System.out.println(LocalDateTime.now(clock));

        List<LocalDateTime> dummy = Arrays.asList(
                LocalDateTime.of(2021, 04, 02, 8, 15),
                LocalDateTime.of(2024, 04, 06, 13, 15),
                LocalDateTime.of(2024, 04, 06, 15, 30),
                LocalDateTime.of(2021, 04, 03, 8, 15),
                LocalDateTime.of(2022, 01, 03, 5, 15),
                LocalDateTime.of(2025, 01, 03, 5, 15)
        );

        logger.info("Hoje 'e: " + LocalDateTime.now(clock));

        var actual = dateHandler.removeFromThePast(dummy);

        logger.info("Todas essas datas devem estar de hoje p frente {}", actual);

        assertEquals(2, actual.size());

    }
}