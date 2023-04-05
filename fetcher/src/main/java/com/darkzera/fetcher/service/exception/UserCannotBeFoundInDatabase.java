package com.darkzera.fetcher.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR,
        reason = "Not able to properly reach user email")
public class UserCannotBeFoundInDatabase extends RuntimeException {}
