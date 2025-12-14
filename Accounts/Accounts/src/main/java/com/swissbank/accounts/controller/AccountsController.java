package com.swissbank.accounts.controller;

import com.swissbank.accounts.constants.AccountConstants;
import com.swissbank.accounts.dto.CustomerDto;
import com.swissbank.accounts.dto.ResponseDto;
import com.swissbank.accounts.service.IAccountsService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/accounts", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class AccountsController {


    private final IAccountsService iAccountsService;
    private IAccountsService accountsService;


    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createAccount(@RequestBody CustomerDto customerDto) {
        accountsService.createAccount(customerDto);
        ResponseDto responseDto = new ResponseDto("200", "Account created successfully");
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/fetch")
    public ResponseEntity<CustomerDto> fetchAccountDetails(@RequestParam String mobileNumber) {
        CustomerDto customerDto = accountsService.fetchAcccountDetails(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK).body(customerDto);
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateAccountDetails(@RequestBody CustomerDto customerDto) {
        boolean isUpdated = accountsService.updateAccontDetails(customerDto);
        if(isUpdated){
            return ResponseEntity.ok(new ResponseDto(AccountConstants.STATUS_CODE_200, AccountConstants.STATUS_MESSAGE_200));
        }else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(AccountConstants.STATUS_CODE_500, AccountConstants.STATUS_MESSAGE_500));
        }

    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteAccountDetails(@RequestParam String mobileNumber) {

        boolean isDeleted = accountsService.deleteAccount(mobileNumber);

        if(isDeleted){
            return ResponseEntity.ok(new ResponseDto(AccountConstants.STATUS_CODE_200, AccountConstants.STATUS_MESSAGE_200));
        }else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(AccountConstants.STATUS_CODE_500, AccountConstants.STATUS_MESSAGE_500));
        }

    }


}
