package com.edu.mum.service.impl;

import com.edu.mum.domain.Order;
import com.edu.mum.domain.User;
import com.edu.mum.repository.OrderRepository;
import com.edu.mum.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public void save(Order order) {
        orderRepository.save(order);
    }

    @Override
    public Order findByUserAndPaid(User user, Boolean paid) {
        return orderRepository.findByUserAndPaid(user,paid);
    }

    @Override
    public Order findById(Long id) {
        Optional<Order> order = orderRepository.findById(id);
        return order.isPresent()?order.get():new Order();
    }
    @Override
    public Page<Order> findAllByUser(User user, int page) {
        return orderRepository.findAllByUser(user,new PageRequest(subtractPageByOne(page),10));
    }

    private int subtractPageByOne(int page) {
        return (page < 1) ? 0 : page - 1;
    }


}
