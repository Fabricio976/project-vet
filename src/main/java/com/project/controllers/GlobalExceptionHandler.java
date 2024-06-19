package com.project.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.project.exeptions.CpfNotFoundException;
import com.project.exeptions.EmailNotFoundException;
import com.project.exeptions.RgNotFoundException;


@RestControllerAdvice
public class GlobalExceptionHandler {

    // Método para manipular exceções EmailNotFoundException
    @ExceptionHandler(EmailNotFoundException.class)
    public ResponseEntity<String> handleEmailNotFoundException(EmailNotFoundException ex) {
        // Retorna uma resposta com status 404 e a mensagem da exceção
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    // Método para manipular exceções CpfNotFoundException
    @ExceptionHandler(CpfNotFoundException.class)
    public ResponseEntity<String> handleCpfNotFoundException(CpfNotFoundException ex) {
        // Retorna uma resposta com status 404 e a mensagem da exceção
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    // Método para manipular exceções RgNotFoundException
    @ExceptionHandler(RgNotFoundException.class)
    public ResponseEntity<String> handleRgNotFoundException(RgNotFoundException ex) {
        // Retorna uma resposta com status 404 e a mensagem da exceção
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }


}