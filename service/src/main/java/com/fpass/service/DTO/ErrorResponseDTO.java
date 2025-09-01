package com.fpass.service.DTO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ErrorResponseDTO {

    private String message;
    private String details;
    private String correlationId;
    private LocalDateTime timestamp;

    public ErrorResponseDTO(String message, String details, String correlationId) {
        this.message = message;
        this.details = details;
        this.correlationId = correlationId;
        this.timestamp = LocalDateTime.now();
    }
}
