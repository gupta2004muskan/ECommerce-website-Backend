package com.example.E_Commerce.E_commerce.Repository.Auth;

import com.example.E_Commerce.E_commerce.Models.Auth.entities.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MyUserRepository extends JpaRepository<MyUser,String> {
    Optional<MyUser> findByUsername(String userName);
    Optional<MyUser> findByEmailId(String userName);
}