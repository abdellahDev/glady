package com.wedoogift.identityprovider.exception.custom;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.Serial;
import java.text.MessageFormat;


@Slf4j
public class WedoogiftServiceUnavailableException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 253956787L;

    @Getter
    private final Object[] args;
    public  WedoogiftServiceUnavailableException(String message,Object... args){
        super(message);
        this.args=args;    }
}
