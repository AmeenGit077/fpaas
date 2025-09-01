package com.fpass.service.exceptionHandler;

import com.fpass.service.DTO.ErrorResponseDTO;
import com.fpass.service.util.CorrelationIdUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(AppException.class)
    public ResponseEntity<ErrorResponseDTO> handleAppException(AppException ex) {
        String correlationId = CorrelationIdUtil.getCorrelationId();

        log.warn("AppException [{}] - Code: {}, Message: {}",
                correlationId, ex.getErrorCode(), ex.getMessage());

        HttpStatus status = switch (ex.getErrorCode()) {
            case USER_NOT_FOUND -> HttpStatus.NOT_FOUND;
            case INVALID_CREDENTIALS -> HttpStatus.UNAUTHORIZED;
            case USER_ALREADY_EXISTS -> HttpStatus.CONFLICT;
            default -> HttpStatus.INTERNAL_SERVER_ERROR;
        };

        ErrorResponseDTO error = new ErrorResponseDTO(
                ex.getMessage(),
                ex.getErrorCode().name(),
                correlationId
        );
        return new ResponseEntity<>(error, status);
    }

    @ExceptionHandler(Exception.class) // fallback
    public ResponseEntity<ErrorResponseDTO> handleGeneric(Exception ex) {
        String correlationId = CorrelationIdUtil.getCorrelationId();

        log.error("Unhandled Exception [{}] - {}", correlationId, ex.getMessage(), ex);

        ErrorResponseDTO error = new ErrorResponseDTO(
                "Internal Server Error",
                ex.getMessage(),
                correlationId
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
