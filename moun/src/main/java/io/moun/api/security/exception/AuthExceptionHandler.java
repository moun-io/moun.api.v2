package io.moun.api.security.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
@Order(1)
public class AuthExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(AuthExceptionHandler.class);


//    @ExceptionHandler(Exception.class)
    public void authenticationExceptionHandler(final Exception e) {
        logger.info(e.getMessage());
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Authentication failed : Bad Request");
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<String> usernameNotFoundExceptionHandler(final UsernameNotFoundException e) {
        logger.info(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Username Not Found with the JWT token. Check the Database" + e.getMessage());
    }

    @ExceptionHandler(AuthenticationCredentialsNotFoundException.class)
    public ResponseEntity<String> authenticationCredentialsNotFoundExceptionHandler(final AuthenticationCredentialsNotFoundException e) {
        logger.info(e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User Credential Not Found : " + e.getMessage());
    }

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<String> usernameAlreadyExistsExceptionHandler(final UsernameAlreadyExistsException e) {
        logger.info(e.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body("Username Already Exists" + e.getMessage());
    }
}


