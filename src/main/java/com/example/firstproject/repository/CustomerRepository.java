package com.example.firstproject.repository;

import com.example.firstproject.model.user.customer.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
