package com.example.unitech.data.repository;

import com.example.unitech.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findUserByPin(String pin);


}
