package com.henriquegross.loans.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.henriquegross.loans.config.LoansConfigServer;
import com.henriquegross.loans.model.Customer;
import com.henriquegross.loans.model.Loans;
import com.henriquegross.loans.model.Properties;
import com.henriquegross.loans.repository.LoansRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LoansController {
    @Autowired
    private LoansRepository loansRepository;

    @Autowired
    LoansConfigServer loansConfigServer;

    @GetMapping("/myLoans")
    public List<Loans> getLoansDetails(@RequestBody Customer customer) {
        List<Loans> loans = loansRepository.findByCustomerIdOrderByStartDtDesc(customer.getCustomerId());
        if (loans != null) {
            return loans;
        } else {
            return null;
        }
    }

    @GetMapping("/loans/properties")
    public String getPropertyDetails() throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        Properties properties = new Properties(loansConfigServer.getMsg(),
                                               loansConfigServer.getBuildVersion(),
                                               loansConfigServer.getMailDetails(),
                                               loansConfigServer.getActiveBranches());
        return ow.writeValueAsString(properties);
    }
}
