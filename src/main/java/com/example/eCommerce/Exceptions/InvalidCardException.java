package com.example.eCommerce.Exceptions;

import jakarta.persistence.criteria.CriteriaBuilder;

public class InvalidCardException extends Exception{
    public InvalidCardException(String message) {
        super(message);
    }
}
