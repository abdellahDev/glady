package com.wedoogift.deposit.exception;



import com.wedoogift.deposit.exception.custom.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
import java.util.Locale;

@ControllerAdvice
@RequiredArgsConstructor
public class WedoogiftGlobalExceptionHandler {

    private final MessageSource messageSource;


    private String getExceptionMessage(String messageCode,Object... args){
        return messageSource.getMessage(messageCode, args, Locale.getDefault());

    }
    @ExceptionHandler(WedoogiftNotFoundException.class)
    public ResponseEntity<ErrorObject> handleWedoogiftNotFoundException(WedoogiftNotFoundException ex, WebRequest request){

        ErrorObject errorObject = ErrorObject.builder()
                                    .errorMessage(getExceptionMessage(ex.getMessage(),ex.getArgs()))
                                    .status(HttpStatus.NOT_FOUND.value())
                                    .timestamp(new Date())
                                                 .build();
            return new ResponseEntity<ErrorObject>(errorObject,HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(WedoogiftServiceUnavailableException.class)
    public ResponseEntity<ErrorObject> handleWedoogiftServiceUnavailableException(WedoogiftServiceUnavailableException ex, WebRequest request){
        ErrorObject errorObject = ErrorObject.builder()
                                             .errorMessage(getExceptionMessage(ex.getMessage(),ex.getArgs()))
                                             .status(HttpStatus.SERVICE_UNAVAILABLE.value())
                                             .timestamp(new Date())
                                             .build();
        return new ResponseEntity<ErrorObject>(errorObject,HttpStatus.SERVICE_UNAVAILABLE);
    }


    @ExceptionHandler(WedoogiftInvalidMessageException.class)
    public ResponseEntity<ErrorObject> handleWedoogiftInvalidMessageException(WedoogiftInvalidMessageException ex, WebRequest request){
        ErrorObject errorObject = ErrorObject.builder()
                                             .errorMessage(getExceptionMessage(ex.getMessage(),ex.getArgs()))
                                             .status(HttpStatus.UNAUTHORIZED.value())
                                             .timestamp(new Date())
                                             .build();
        return new ResponseEntity<ErrorObject>(errorObject,HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(WedoogiftBadRequestException.class)
    public ResponseEntity<ErrorObject> handleWedoogiftBadRequestException(WedoogiftBadRequestException ex, WebRequest request){
        ErrorObject errorObject = ErrorObject.builder()
                                             .errorMessage(getExceptionMessage(ex.getMessage(),ex.getArgs()))
                                             .status(HttpStatus.BAD_REQUEST.value())
                                             .timestamp(new Date())
                                             .build();
        return new ResponseEntity<ErrorObject>(errorObject,HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(WedoogiftUnauthorizedRequestException.class)
    public ResponseEntity<ErrorObject> handleWedoogiftUnauthorizedRequestException(WedoogiftUnauthorizedRequestException ex, WebRequest request){
        ErrorObject errorObject = ErrorObject.builder()
                                             .errorMessage(getExceptionMessage(ex.getMessage(),ex.getArgs()))
                                             .status(HttpStatus.UNAUTHORIZED.value())
                                             .timestamp(new Date())
                                             .build();
        return new ResponseEntity<ErrorObject>(errorObject,HttpStatus.UNAUTHORIZED);
    }

}
