package com.project.demo.exceptions;

import org.springframework.http.HttpStatus;

public class ResponseException extends Exception{
    private HttpStatus status;


    public ResponseException(String message, HttpStatus status){
        super(message);
        this.status=status;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }
}
