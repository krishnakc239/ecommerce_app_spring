package com.edu.mum.service;

import com.edu.mum.domain.CartItem;
import com.edu.mum.domain.User;

import java.util.List;
import java.util.Optional;

public interface CartItemService {

    void save(CartItem cartItem);

    //List<CartItem> findAllByUser();
    void delete(CartItem cartItem);

    Double getSubTotal();

    int getNumberOfProductsByUser();

    void deleteById(Long aLong);

    Optional<CartItem> findById(Long aLong);

    List<CartItem> findAllByUserAndDelivered(User user,boolean delev);
}
