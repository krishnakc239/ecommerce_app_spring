package com.edu.mum.service;

import com.edu.mum.domain.CartItem;

import java.util.List;

public interface CartItemService {

    void save(CartItem cartItem);
    List<CartItem> findAll();
    void delete(CartItem cartItem);
}
