package io.moun.api.security.exception;

import javax.security.sasl.AuthenticationException;

public class UsernameAlreadyExistsException extends RuntimeException {
    public UsernameAlreadyExistsException(String message) {
        super(message);  // 부모 클래스인 RuntimeException의 생성자 호출
    }
}
