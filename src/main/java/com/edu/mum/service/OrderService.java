package com.edu.mum.service;

import com.edu.mum.domain.Order;
import com.edu.mum.domain.User;
import org.springframework.data.domain.Page;

public interface OrderService {
    void save(Order order);
    Order findByUserAndPaid(User user, Boolean paid);
    Order findById(Long id);
    Page<Order> findAllByUser(User user, int page);
    Page<Order> findAll(int page);
    Order findOrderById(Long id);
}
