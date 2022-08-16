package com.henriquegross.loans.repository;

import com.henriquegross.loans.model.Loans;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LoansRepository extends CrudRepository<Loans, Long> {
        List<Loans> findByCustomerIdOrderByStartDtDesc(int customerId);
}
