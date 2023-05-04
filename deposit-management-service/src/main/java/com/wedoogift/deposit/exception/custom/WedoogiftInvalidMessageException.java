package com.wedoogift.deposit.exception.custom;


import lombok.Getter;

import java.io.Serial;

public class WedoogiftInvalidMessageException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 253556787L;


    @Getter
    private final Object[] args;

    public WedoogiftInvalidMessageException(String message,Object... args){
        super(message);
        this.args= args;
    }



}