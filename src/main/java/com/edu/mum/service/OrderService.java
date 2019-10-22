package com.edu.mum.service;

import com.edu.mum.domain.Order;
import com.edu.mum.domain.User;
import org.springframework.data.domain.Page;

public interface OrderService {
    Page<Order> findAllByUser(User user, int page);
}
