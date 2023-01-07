package com.lio.api.util;

import com.lio.api.model.dto.ApiResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Map;


public class CustomResponse{

    private CustomResponse(){};

    public static ResponseEntity<ApiResponse<Object>> getResponse(
            Object data ,
            String message
    ){
        ApiResponse<Object> response = ApiResponse.builder()
                .timestamp(LocalDateTime.now())
                .ok(true)
                .message( message == null ? "Success!" : message )
                .status(HttpStatus.OK.value())
                .errors(null)
                .data(data)
                .build();
        return ResponseEntity.ok(response);
    }

    public static ResponseEntity<ApiResponse<Object>> getResponse(
            Object data ,
            HttpHeaders httpHeaders,
            String message
    ){
        ApiResponse<Object> response = ApiResponse.builder()
                .timestamp(LocalDateTime.now())
                .ok(true)
                .message( message == null ? "Success!" : message )
                .status(HttpStatus.OK.value())
                .errors(null)
                .data(data)
                .build();
        return new ResponseEntity<ApiResponse<Object>>(
               response ,
               httpHeaders ,
               HttpStatus.OK
        );
    }

    public static ResponseEntity<ApiResponse<Object>> getErrorResponse(
            Object data ,
            String message ,
            Map<String,String> errors
    ){
        ApiResponse<Object> response = ApiResponse.builder()
                .timestamp(LocalDateTime.now())
                .ok(false)
                .message( message == null ? "Failed!" : message )
                .status(HttpStatus.BAD_REQUEST.value())
                .errors(errors)
                .data(data)
                .build();
        return ResponseEntity.badRequest().body(response);
    }

}
