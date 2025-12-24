package com.swissbank.Loans.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Schema(name = "ErrorResponse",
        description = "Data Transfer Object for Error Response details")
@Data @AllArgsConstructor
public class ErrorResponseDto {

    @Schema(description = "API Path where the error occurred")
    private String apiPath;
    @Schema(description = "HTTP Status Code representing the error")
    private HttpStatus errorCode;
    @Schema(description = "Detailed Error Message")
    private String errorMessage;
    @Schema(description = "Timestamp when the error occurred")
    private LocalDateTime errorTime;
}
