package com.example.E_Commerce.E_commerce.Repository;

import com.example.E_Commerce.E_commerce.Models.Auth.entities.MyUser;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class Insertrepo {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void insertUserWithQuery(MyUser person) {
        entityManager.createNativeQuery("INSERT INTO userss ( user_name, password, roles) VALUES (?,?,?)")
                .setParameter(1, person.getUsername())
                .setParameter(2, person.getPassword())
                .setParameter(3, person.getRoles())
                .executeUpdate();
    }
}