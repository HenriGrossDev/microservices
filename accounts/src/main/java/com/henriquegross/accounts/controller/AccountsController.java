package com.henriquegross.accounts.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.henriquegross.accounts.config.AccountsServiceConfig;
import com.henriquegross.accounts.model.*;
import com.henriquegross.accounts.repository.AccountsRepository;
import com.henriquegross.accounts.service.client.CardsFeignClient;
import com.henriquegross.accounts.service.client.LoansFeignClient;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class AccountsController {

    @Autowired
    private AccountsRepository accountsRepository;

    @Autowired
    AccountsServiceConfig accountsConfig;

    @Autowired
    LoansFeignClient loansFeignClient;

    @Autowired
    CardsFeignClient cardsFeignClient;

    @PostMapping("/myaccount")
    public Accounts getAccountDetails(@RequestBody Customer customer){
        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId());
        if (accounts != null){
            return accounts;
        }else{
            return null;
        }
    }

    @GetMapping("/account/properties")
    public String getPropertyDetails() throws JsonProcessingException{
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        Properties properties = new Properties(accountsConfig.getMsg(),
                                               accountsConfig.getBuildVersion(),
                                               accountsConfig.getMailDetails(),
                                               accountsConfig.getActiveBranches());
        return ow.writeValueAsString(properties);
    }

    @PostMapping("/myCustomerDetails")
    public CustomerDetails myCustomerDetails(@RequestBody Customer customer) {

        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId());
        List<Loans> loans = loansFeignClient.getLoansDetails(customer);
        List<Cards> cards = cardsFeignClient.getCardDetails(customer);

        CustomerDetails customerDetails = new CustomerDetails();
        customerDetails.setAccounts(accounts);
        customerDetails.setLoans(loans);
        customerDetails.setCards(cards);

        return customerDetails;


        }
    }
