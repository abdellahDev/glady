package com.wedoogift.deposit.exception.custom;

import lombok.Getter;

import java.io.Serial;


public class WedoogiftUnauthorizedRequestException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 85543556787L;

    @Getter
    private final Object[] args;
    public WedoogiftUnauthorizedRequestException(String message, Object... args){
        super(message);
        this.args=args;
    }
}
