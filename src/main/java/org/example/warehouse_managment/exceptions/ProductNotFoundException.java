package org.example.warehouse_managment.exceptions;


public class ProductNotFoundException extends Exception {
    public ProductNotFoundException(String message) {
        super(message);
    }
}