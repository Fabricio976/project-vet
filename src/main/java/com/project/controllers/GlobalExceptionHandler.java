package com.project.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.project.exeptions.EmailNotFoundException;


@RestControllerAdvice
public class GlobalExceptionHandler {

    // Método para manipular exceções do tipo EmailNotFoundException
    @ExceptionHandler(EmailNotFoundException.class)
    public ResponseEntity<String> handleEmailNotFoundException(EmailNotFoundException ex) {
        // Retorna uma resposta com status 404 e a mensagem da exceção
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}