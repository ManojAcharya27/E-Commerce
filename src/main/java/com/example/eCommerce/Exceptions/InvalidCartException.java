package com.example.eCommerce.Exceptions;

public class InvalidCartException extends Exception{
    public InvalidCartException(String message){
        super(message);
    }
}
