package com.wedoogift.identityprovider.exception.custom;

import lombok.Getter;

import java.io.Serial;


public class WedoogiftBadRequestException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 853556787L;

    @Getter
    private final Object[] args;
    public  WedoogiftBadRequestException(String message,Object... args){
        super(message);
        this.args=args;
    }
}
