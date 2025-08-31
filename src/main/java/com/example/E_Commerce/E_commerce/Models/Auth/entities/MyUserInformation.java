package com.example.E_Commerce.E_commerce.Models.Auth.entities;

import com.example.E_Commerce.E_commerce.Models.Auth.models.Address;
import jakarta.persistence.MapsId;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "users_info")
@NoArgsConstructor
@RequiredArgsConstructor
@Data
public class MyUserInformation implements Serializable {
    @Id
    @Column(name = "username")
    private String username;

    @NonNull
    @OneToOne
    @MapsId
    @JoinColumn(name = "username")
    private MyUser myUser;
    @NonNull
    @Embedded
    private Address address;

    @NonNull
    private String phoneNumber;
}
