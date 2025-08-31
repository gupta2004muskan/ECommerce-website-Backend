package com.example.E_Commerce.E_commerce.Repository.Auth;

import com.example.E_Commerce.E_commerce.Models.Auth.entities.MyUserInformation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyUserInformationRepository extends JpaRepository<MyUserInformation,String> {
}
