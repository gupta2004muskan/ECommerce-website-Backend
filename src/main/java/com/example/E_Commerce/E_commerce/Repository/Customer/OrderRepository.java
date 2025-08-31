package com.example.E_Commerce.E_commerce.Repository.Customer;
import com.example.E_Commerce.E_commerce.Models.Auth.entities.MyUser;
import com.example.E_Commerce.E_commerce.Models.Customer.entites.MyUserOrders;
import com.example.E_Commerce.E_commerce.Models.Customer.entites.models.OrderTrackingStatus;
import com.example.E_Commerce.E_commerce.Models.Customer.models.CustomerOrderID;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<MyUserOrders, CustomerOrderID> {
    Optional<List<MyUserOrders>> findAllByUsername(MyUser username);
    Optional<MyUserOrders> findById(Long id);
    Optional<List<MyUserOrders>> findAllByOrderTrackingStatusIsNotAndOrderTrackingStatusIsNot(OrderTrackingStatus orderTrackingStatus1,OrderTrackingStatus orderTrackingStatus2);
    Optional<List<MyUserOrders>> findAllByOrderTrackingStatus(OrderTrackingStatus orderTrackingStatus);

}