package com.lio.api.exception.handler;

import com.lio.api.model.dto.ApiResponse;
import com.lio.api.util.CustomResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

import static com.lio.api.model.constant.Messages.UNEXPECTED_ERROR;
import static com.lio.api.exception.custom.Index.DuplicateAccountException;

@RestControllerAdvice
public class Index {

    /*
     Global exception handler
     */
    @ExceptionHandler( { Exception.class } )
    public ResponseEntity<ApiResponse<Object>> globalExceptionHandler( Exception e ){
        Map<String,String> errorMap = new HashMap<>();
        errorMap.put( "message" , UNEXPECTED_ERROR );
        return CustomResponse.getErrorResponse( null , UNEXPECTED_ERROR , errorMap );
    }

    /*
     for duplicate account handler
     */
    @ExceptionHandler( { DuplicateAccountException.class } )
    public ResponseEntity<ApiResponse<Object>> duplicateAccountHandler( DuplicateAccountException e ){
        Map<String,String> errorMap = new HashMap<>();
        errorMap.put("message",e.getMessage());
        return CustomResponse.getErrorResponse( null , e.getMessage() , errorMap );
    }

}
