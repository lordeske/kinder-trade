package com.kinder_figurice.exceptions;

public class UserNameExistException extends RuntimeException{


    public UserNameExistException(String poruka) {
        super(poruka);
    }
}
