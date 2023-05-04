package com.wedoogift.identityprovider.exception;

import lombok.Builder;

import java.util.Date;


@Builder
public record ErrorObject(Integer status,
                          String errorMessage,
                          Date timestamp) {

}
