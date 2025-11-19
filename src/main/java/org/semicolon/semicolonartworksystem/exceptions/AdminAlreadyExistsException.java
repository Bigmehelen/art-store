package org.semicolon.semicolonartworksystem.exceptions;

public class AdminAlreadyExistsException extends RuntimeException {
    public AdminAlreadyExistsException(String message) {
        super(message);
    }
}
