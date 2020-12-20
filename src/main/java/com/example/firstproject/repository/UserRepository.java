package com.example.firstproject.repository;

import com.example.firstproject.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findUsersByUsername(String name);

}
