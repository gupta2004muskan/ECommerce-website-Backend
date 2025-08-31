package com.example.E_Commerce.E_commerce.Repository.Auth;

import com.example.E_Commerce.E_commerce.Models.Auth.entities.MyUserAuth;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyUserAuthRepository extends JpaRepository<MyUserAuth,String> {
}
