package com.henriquegross.accounts.controller;

import com.henriquegross.accounts.model.Accounts;
import com.henriquegross.accounts.model.Customer;
import com.henriquegross.accounts.repository.AccountsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountsController {

    @Autowired
    private AccountsRepository accountsRepository;

    @PostMapping("/myaccount")
    public Accounts getAccountDetails(@RequestBody Customer customer){
        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId());
        if (accounts != null){
            return accounts;
        }else{
            return null;
        }
    }
}
