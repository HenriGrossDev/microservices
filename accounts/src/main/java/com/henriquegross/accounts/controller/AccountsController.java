package com.henriquegross.accounts.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.henriquegross.accounts.config.AccountsServiceConfig;
import com.henriquegross.accounts.model.Accounts;
import com.henriquegross.accounts.model.Customer;
import com.henriquegross.accounts.model.Properties;
import com.henriquegross.accounts.repository.AccountsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class AccountsController {

    @Autowired
    private AccountsRepository accountsRepository;

    @Autowired
    AccountsServiceConfig accountsConfig;

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
}
