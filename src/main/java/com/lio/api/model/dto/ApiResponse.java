package com.lio.api.model.dto;

import java.time.LocalDateTime;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {
    
    private LocalDateTime timestamp;
    private Integer status;
    private Boolean ok;
    private String message;
    private T data;
    private Map<String,String> errors;

}
