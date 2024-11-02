package com.kinder_figurice.exceptions;

public class EmailConflictException extends RuntimeException {

    public EmailConflictException(String poruka)
    {
        super(poruka);
    }



}
