package ru.min.resaleplatform.exception;

public class AccessException extends RuntimeException {

    private String message;

    public AccessException(String message) {
        this.message = message;
    }
}
