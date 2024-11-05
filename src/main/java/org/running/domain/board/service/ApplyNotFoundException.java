package org.running.domain.board.service;

public class ApplyNotFoundException extends RuntimeException{
    public ApplyNotFoundException(String message){
        super(message);
    }
}
