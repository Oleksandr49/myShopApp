package com.example.firstproject.repository;

import com.example.firstproject.model.user.customer.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
