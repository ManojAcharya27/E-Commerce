package com.example.eCommerce.Exceptions;

public class EmailAlreadyPresentException extends Exception{
    public  EmailAlreadyPresentException(String message){
        super(message);
    }
}
