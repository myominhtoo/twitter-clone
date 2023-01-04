package com.lio.api.model.dto;

import java.time.LocalDateTime;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiResponse<T> {

    @JsonFormat(  shape =  JsonFormat.Shape.STRING , pattern = "yyyy-MM-dd hh:mm:ss" )
    private LocalDateTime timestamp;
    private Integer status;
    private Boolean ok;
    private String message;
    private T data;
    private Map<String,String> errors;

}
