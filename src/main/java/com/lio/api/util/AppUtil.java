package com.lio.api.util;

import org.springframework.validation.BindingResult;

import java.util.HashMap;
import java.util.Map;

public class AppUtil {

    private AppUtil(){}

    public static Map<String,String> getErrorsMapFromBindingResults(BindingResult bindingResult){
        Map<String,String> errorsMap = new HashMap<>();
        bindingResult.getFieldErrors()
                .stream()
                .forEach( fieldError -> {
                    errorsMap.put( fieldError.getField() , fieldError.getDefaultMessage() );
                });
        return errorsMap;
    }

}
