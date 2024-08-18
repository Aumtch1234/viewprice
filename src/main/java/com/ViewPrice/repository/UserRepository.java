package com.ViewPrice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ViewPrice.modal.User;

public interface UserRepository extends JpaRepository <User, Long>{


    User findByEmail(String email);

}
