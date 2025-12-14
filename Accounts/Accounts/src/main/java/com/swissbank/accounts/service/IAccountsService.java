package com.swissbank.accounts.service;

import com.swissbank.accounts.dto.CustomerDto;

public interface IAccountsService {



/*

 @param() customerDto - CustomerDto object containing customer details
 */


    void createAccount(CustomerDto customerDto);

    CustomerDto fetchAcccountDetails(String mobileNumber);

    boolean updateAccontDetails(CustomerDto customerDto);

    boolean deleteAccount(String mobileNumber);

}
