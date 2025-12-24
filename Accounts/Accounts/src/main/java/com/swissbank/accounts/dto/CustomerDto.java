package com.swissbank.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Schema(name = "Customer",
        description = "Data Transfer Object for Customer details")
@Data
public class CustomerDto {

    @Schema(description = "Customer Name", example = "John Doe", required = true)
    @NotEmpty(message = "name cannot be null or empty")
    @Size(min = 5, max = 30, message = "name must be between 5 and 30 characters")
    private String name;


    @Schema(name = "Email",
            description = "Customer Email Address",
            example = "johndoe@swissbank.com")
    @NotEmpty(message = "email cannot be null or empty")
    @Email(message = "email should be valid value")
    private String email;

    @Schema(name = "Mobile Number",
            description = "Customer Mobile Number",
            example = "9876543210")
    @NotEmpty
    @Pattern(regexp = "(^$|[0-9]{10})", message = "mobile number must be 10 digits")
    private String mobileNumber;
    private AccountsDto accountsDto;
}
