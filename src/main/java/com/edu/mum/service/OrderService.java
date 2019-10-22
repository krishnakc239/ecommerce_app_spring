package com.edu.mum.service;

import com.edu.mum.domain.Order;
import com.edu.mum.domain.User;

public interface OrderService {
    void save(Order order);
    Order findByUserAndPaid(User user, Boolean paid);
    Order findById(Long id);
}
