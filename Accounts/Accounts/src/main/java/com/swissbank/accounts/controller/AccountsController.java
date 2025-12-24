package com.swissbank.accounts.controller;

import com.swissbank.accounts.constants.AccountConstants;
import com.swissbank.accounts.dto.CustomerDto;
import com.swissbank.accounts.dto.ErrorResponseDto;
import com.swissbank.accounts.dto.ResponseDto;
import com.swissbank.accounts.service.IAccountsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Accounts Controller", description = "APIs for managing customer accounts performing CRUD operations")
@RequestMapping(path = "/api/v1/accounts", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
@Validated
public class AccountsController {


    private final IAccountsService iAccountsService;
    private IAccountsService accountsService;


    @Operation(summary = "Create a new customer account",
            description = "Creates a new customer account with the provided details")
    @ApiResponse(responseCode = "201", description = "Account created successfully")
    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createAccount(@Valid @RequestBody CustomerDto customerDto) {
        accountsService.createAccount(customerDto);
        ResponseDto responseDto = new ResponseDto("200", "Account created successfully");
        return ResponseEntity.ok(responseDto);
    }

    @Operation(summary = "Fetch customer account details",
            description = "Fetches the account details of a customer using their mobile number")
    @ApiResponse(responseCode = "200", description = "Account details fetched successfully")
    @GetMapping("/fetch")
    public ResponseEntity<CustomerDto> fetchAccountDetails(@RequestParam String mobileNumber) {
        CustomerDto customerDto = accountsService.fetchAcccountDetails(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK).body(customerDto);
    }

    @Operation(summary = "Update customer account details",
            description = "Updates the account details of a customer with the provided information")
    @ApiResponse(responseCode = "200", description = "Account details updated successfully")
    @ApiResponse(responseCode = "500", description = "Internal server error while updating account details",
            content = @Content(
                    schema = @Schema(implementation = ErrorResponseDto.class)
            )

    )
    @ApiResponse(responseCode = "417", description = "Expectation Failed while updating account details",
            content = @Content(
                    schema = @Schema(implementation = ErrorResponseDto.class)
            )

    )
    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateAccountDetails(@Valid@RequestBody CustomerDto customerDto) {
        boolean isUpdated = accountsService.updateAccontDetails(customerDto);
        if(isUpdated){
            return ResponseEntity.ok(new ResponseDto(AccountConstants.STATUS_CODE_200, AccountConstants.STATUS_MESSAGE_200));
        }else {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(AccountConstants.STATUS_CODE_417, AccountConstants.STATUS_MESSAGE_417));
        }

    }

    @Operation(summary = "Delete customer account",
            description = "Deletes the account of a customer using their mobile number")
    @ApiResponse(responseCode = "200", description = "Account deleted successfully")
    @ApiResponse(responseCode = "500", description = "Internal server error while deleting account",
            content = @Content(
                    schema = @Schema(implementation = ErrorResponseDto.class)
            )

    )
    @ApiResponse(responseCode = "417", description = "Expectation Failed while updating account details",
            content = @Content(
                    schema = @Schema(implementation = ErrorResponseDto.class)
            )

    )
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteAccountDetails(@RequestParam@Pattern(regexp = "(^$|[0-9]{10})") String mobileNumber) {

        boolean isDeleted = accountsService.deleteAccount(mobileNumber);

        if(isDeleted){
            return ResponseEntity.ok(new ResponseDto(AccountConstants.STATUS_CODE_200, AccountConstants.STATUS_MESSAGE_200));
        }else {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(AccountConstants.STATUS_CODE_417, AccountConstants.STATUS_MESSAGE_417));
        }

    }


}
