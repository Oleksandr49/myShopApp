package com.example.firstproject.repository;

import com.example.firstproject.model.users.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findUsersByUsername(String name);

}
