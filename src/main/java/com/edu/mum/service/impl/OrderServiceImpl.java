package com.edu.mum.service.impl;

import com.edu.mum.domain.Order;
import com.edu.mum.domain.User;
import com.edu.mum.repository.OrderRepository;
import com.edu.mum.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    private OrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Page<Order> findAllByUser(User user,int page) {
        return orderRepository.findAllByUser(user,new PageRequest(subtractPageByOne(page),10));
    }

    private int subtractPageByOne(int page) {
        return (page < 1) ? 0 : page - 1;
    }
}
