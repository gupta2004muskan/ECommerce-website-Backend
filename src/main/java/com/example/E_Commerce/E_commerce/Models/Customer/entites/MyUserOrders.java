package com.example.E_Commerce.E_commerce.Models.Customer.entites;
import com.example.E_Commerce.E_commerce.Models.Auth.entities.MyUser;
import com.example.E_Commerce.E_commerce.Models.Customer.entites.models.OrderPaymentStatus;
import com.example.E_Commerce.E_commerce.Models.Customer.entites.models.OrderTrackingStatus;
import com.example.E_Commerce.E_commerce.Models.Customer.models.CustomerOrderID;
import com.example.E_Commerce.E_commerce.Models.Customer.models.OrderedProduct;
import com.razorpay.Customer;
import jakarta.persistence.MapsId;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "MyOrders")
@IdClass(CustomerOrderID.class)
public class MyUserOrders implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Id
    @NonNull
    @MapsId("username")
    @ManyToOne
    private MyUser username;

    private String orderId;

    @NonNull
    @OneToMany(cascade = CascadeType.ALL)
//    @OneToMany(mappedBy="uniqueProductOptions")
    private Set<OrderedProduct> orderedProduct;

    @NonNull
    private float totalPayment;

    @NonNull
    @Enumerated(EnumType.ORDINAL)
    private OrderPaymentStatus paymentStatus;

    @NonNull
    @Enumerated(EnumType.ORDINAL)
    private OrderTrackingStatus orderTrackingStatus;

    @NonNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date utilDate;

    @NonNull
    @Temporal(TemporalType.DATE)
    private Calendar utilCalendar;

}
