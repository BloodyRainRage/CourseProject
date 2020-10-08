package com.baddragon.Exceptions;

public class UnknownTypeOfCargoException extends Exception {

    public UnknownTypeOfCargoException(String errorMessage){
        super(errorMessage);
    }
}
