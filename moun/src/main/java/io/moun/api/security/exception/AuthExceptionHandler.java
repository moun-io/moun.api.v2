package io.moun.api.security.exception;

import io.moun.api.security.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.security.sasl.AuthenticationException;

@RestControllerAdvice
public class AuthExceptionHandler {

    private final Logger LOGGER = LoggerFactory.getLogger(AuthService.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> authenticationExceptionHandler(final Exception e) {
        LOGGER.info(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
    @ExceptionHandler(AuthenticationCredentialsNotFoundException.class)
    public ResponseEntity<String> authenticationCredentialsNotFoundExceptionHandler(final AuthenticationCredentialsNotFoundException e) {
        LOGGER.info(e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User Credential Not Found : " +  e.getMessage());
    }
    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<String>UsernameAlreadyExistsExceptionHandler(final UsernameAlreadyExistsException e) {
        LOGGER.info(e.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body("Username Already Exists"+ e.getMessage());
    }
}

