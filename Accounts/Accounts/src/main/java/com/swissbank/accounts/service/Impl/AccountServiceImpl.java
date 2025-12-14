package com.swissbank.accounts.service.Impl;


import com.swissbank.accounts.constants.AccountConstants;
import com.swissbank.accounts.dto.AccountsDto;
import com.swissbank.accounts.dto.CustomerDto;
import com.swissbank.accounts.entites.Accounts;
import com.swissbank.accounts.entites.Customer;
import com.swissbank.accounts.exception.CustomerAlreadyExistException;
import com.swissbank.accounts.exception.ResourceNotFoundtException;
import com.swissbank.accounts.mapper.AccountsMapper;
import com.swissbank.accounts.mapper.CustomerMapper;
import com.swissbank.accounts.repositories.AccountsRepository;
import com.swissbank.accounts.repositories.CustomerRepository;
import com.swissbank.accounts.service.IAccountsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements IAccountsService {

    private AccountsRepository accountsRepository;
    private CustomerRepository customerRepository;


    @Override
    public void createAccount(CustomerDto customerDto) {

        Customer customer = CustomerMapper.mapToCustomer(customerDto, new Customer());
        Optional<Customer> optionalCustomer = customerRepository.findByMobileNumber(customer.getMobileNumber());
        if(optionalCustomer.isPresent()){
            throw  new CustomerAlreadyExistException("Customer already registered with give mobile number "+optionalCustomer.get().getMobileNumber());
        }
        customer.setCreatedAt(LocalDateTime.now());
        customer.setCreatedBy("System User");

        Customer savedCustomer = customerRepository.save(customer);
        accountsRepository.save(createNewAccount(savedCustomer));


    }



    private Accounts createNewAccount(Customer customer) {

        Accounts newAccounts = new Accounts();
        newAccounts.setCustomerId(customer.getCustomerId());
        long randomAccNumber = 1000000000L + new Random().nextInt(900000000);
        newAccounts.setAccountNumber(randomAccNumber);
        newAccounts.setAccountType(AccountConstants.SAVINGS);
        newAccounts.setBranchAddress(AccountConstants.ADDRESS);
        return newAccounts;

    }

    @Override
    public CustomerDto fetchAcccountDetails(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(() -> new ResourceNotFoundtException(
                "Customer", "mobile number", mobileNumber));

        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(() -> new ResourceNotFoundtException(
                "Account", "customer id", customer.getCustomerId().toString()));

        CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer, new CustomerDto());
        AccountsDto accountsDto = AccountsMapper.mapToAccountsDto(accounts, new AccountsDto());
        customerDto.setAccountsDto(accountsDto);
        return customerDto;
    }

    @Override
    public boolean updateAccontDetails(CustomerDto customerDto) {
        boolean isUpdated = false;
        AccountsDto accountsDto = customerDto.getAccountsDto();
        if(accountsDto!=null){
            Accounts accounts = accountsRepository.findById(accountsDto.getAccountNumber()).orElseThrow(() -> new ResourceNotFoundtException(
                    "Account", "account number", accountsDto.getAccountNumber().toString()
            ));
            System.out.println("db fetched account : "+accounts);
            AccountsMapper.mapToAccounts(accountsDto, accounts);


            System.out.println("mapped account : "+accounts);

            accounts = accountsRepository.save(accounts);
            System.out.println("saved account : "+accounts);

            long customerId = accounts.getCustomerId();
            Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new ResourceNotFoundtException(
                    "Customer", "customer id", String.valueOf(customerId)
            ));
            CustomerMapper.mapToCustomer(customerDto, customer);
            customerRepository.save(customer);
            isUpdated = true;

        }

        return isUpdated;
    }

    @Override
    public boolean deleteAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(() -> new ResourceNotFoundtException(
                "Customer", "mobile number", mobileNumber
        ));
        accountsRepository.deleteByCustomerId(customer.getCustomerId());
        customerRepository.deleteById((customer.getCustomerId()));
        return true;
    }


}
