package com.edu.mum.service;

import com.edu.mum.domain.CartItem;

import java.util.List;
import java.util.Optional;

public interface CartItemService {

    void save(CartItem cartItem);

    List<CartItem> findAllByUser();
    void delete(CartItem cartItem);

    Double getSubTotal();

    int getNumberOfProducts();

    void deleteById(Long aLong);

    Optional<CartItem> findById(Long aLong);
}
