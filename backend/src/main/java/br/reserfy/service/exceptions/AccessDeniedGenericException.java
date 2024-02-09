package br.reserfy.service.exceptions;

public class AccessDeniedGenericException extends RuntimeException{
    public AccessDeniedGenericException(String message) {
        super(message);
    }
}
