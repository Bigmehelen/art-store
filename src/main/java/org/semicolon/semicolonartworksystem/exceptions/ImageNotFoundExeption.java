package org.semicolon.semicolonartworksystem.exceptions;

public class ImageNotFoundExeption extends RuntimeException {
    public ImageNotFoundExeption(){
        super("Image not found");
    }
}
