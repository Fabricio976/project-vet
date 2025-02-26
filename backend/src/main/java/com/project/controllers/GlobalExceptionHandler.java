package com.project.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.project.exeptions.CpfNotFoundException;
import com.project.exeptions.EmailNotFoundException;
import com.project.exeptions.InvalidCredentialsException;
import com.project.exeptions.RgNotFoundException;

/**
 * Classe de manipulação global de exceções para a aplicação.
 * Cada método de tratamento de exceção lida com uma exceção personalizada e retorna uma resposta HTTP apropriada para o cliente.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Essa exceção é lançada quando um email não é encontrado no sistema.
     *
     * @param ex A exceção {@link EmailNotFoundException} que foi lançada.
     * @return Resposta HTTP com status 404 (Not Found) e a mensagem de erro associada à exceção.
     */
    @ExceptionHandler(EmailNotFoundException.class)
    public ResponseEntity<String> handleEmailNotFoundException(EmailNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    /**
     * Essa exceção é lançada quando o CPF fornecido não é encontrado no sistema.
     *
     * @param ex A exceção {@link CpfNotFoundException} que foi lançada.
     * @return Resposta HTTP com status 404 (Not Found) e a mensagem de erro associada à exceção.
     */
    @ExceptionHandler(CpfNotFoundException.class)
    public ResponseEntity<String> handleCpfNotFoundException(CpfNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    /**
     * Essa exceção é lançada quando o RG fornecido não é encontrado no sistema.
     *
     * @param ex A exceção {@link RgNotFoundException} que foi lançada.
     * @return Resposta HTTP com status 404 (Not Found) e a mensagem de erro associada à exceção.
     */
    @ExceptionHandler(RgNotFoundException.class)
    public ResponseEntity<String> handleRgNotFoundException(RgNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    /**
     * Método responsável por tratar a exceção {@link InvalidCredentialsException}.
     *
     * @param e A exceção {@link InvalidCredentialsException} que foi lançada.
     * @return Resposta HTTP com status 403 (Forbidden) e a mensagem de erro associada à exceção.
     */
    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<String> handleInvalidCredentialsException(InvalidCredentialsException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
    }
}
