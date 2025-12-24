package com.swissbank.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Schema(name = "Accounts",
        description = "Data Transfer Object for Account details")
@Data
public class AccountsDto {

    @Schema(description = "Account Number", example = "1234567890", required = true)
    @NotEmpty(message = "account number cannot be null or empty")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "account number must be 10 digits")
    private Long accountNumber;

    @Schema(description = "Account Type", example = "Savings", required = true)
    @NotEmpty(message = "account type cannot be null or empty")
    private String accountType;

    @Schema(description = "Branch Address", example = "123 Main St, City, Country", required = true)
    @NotEmpty(message = "branch address type cannot be null or empty")
    private String branchAddress;
}
