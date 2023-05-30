package com.darkzera.fetcher.service.exception;

public class ClientException extends RuntimeException{
    private final String message;

    public ClientException(String message) {
        this.message = message;
    }

    public ClientException() {
       this.message = "Contact API admin";
    }
}
