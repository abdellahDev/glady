package com.wedoogift.deposit.exception.custom;


import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.Serial;

@Slf4j
public class WedoogiftNotFoundException extends  RuntimeException{
    @Serial
    private static final long serialVersionUID = 123456789L;

    @Getter
    private final Object[] args;
    public  WedoogiftNotFoundException(String message,Object... args){
        super(message);
        this.args=args;    }
}
