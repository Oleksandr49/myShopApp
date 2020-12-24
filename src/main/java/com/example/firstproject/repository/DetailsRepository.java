package com.example.firstproject.repository;

import com.example.firstproject.model.user.customer.Details;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetailsRepository extends JpaRepository<Details, Long> {
}
