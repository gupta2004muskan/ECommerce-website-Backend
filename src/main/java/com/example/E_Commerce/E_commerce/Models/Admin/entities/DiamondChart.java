package com.example.E_Commerce.E_commerce.Models.Admin.entities;

import com.example.E_Commerce.E_commerce.Models.Admin.entities.embedables.Diamond;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "diamond_chart")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiamondChart implements Serializable {

    @NonNull
    @EmbeddedId
    private Diamond diamond;

    @NonNull
    @Column(precision = 5, scale = 4)
    private double weight;
}
