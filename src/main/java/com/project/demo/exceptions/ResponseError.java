package com.project.demo.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseError {
    private int statusCode;
    private String message;
    private long timestamp;
    private String stackTrace;

}
