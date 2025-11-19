package org.semicolon.semicolonartworksystem.exceptions;

public class AdminNotFoundException extends RuntimeException {
    public AdminNotFoundException(String message) {
        super(message);
    }
}
