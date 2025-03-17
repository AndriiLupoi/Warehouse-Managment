package org.example.warehouse_managment.exceptions;

public class MovementsNotFoundException extends RuntimeException {
    public MovementsNotFoundException(String message) {
        super(message);
    }
}
