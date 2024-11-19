package io.moun.api.common.excpetion;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.logging.Logger;

@RestControllerAdvice
@Order(999)
public class GlobalException {

    static final private Logger logger = Logger.getLogger(GlobalException.class.getName());

    // 특정 예외 처리 (예: Member 관련 예외)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        // 예외 메시지와 상태 코드 설정
        logger.info(ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

