package com.swissbank.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Schema(name = "Response",
        description = "Data Transfer Object for Response details")
@Data @AllArgsConstructor
public class ResponseDto {

    @Schema(description = "Status Code", example = "200", required = true)
    private String statusCode;
    @Schema(description = "Status Message", example = "Account created successfully", required = true)
    private String statusMessage;
}
