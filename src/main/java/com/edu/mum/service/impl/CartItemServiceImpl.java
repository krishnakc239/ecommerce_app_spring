package com.edu.mum.service.impl;

import com.edu.mum.domain.CartItem;
import com.edu.mum.repository.CartItemRepository;
import com.edu.mum.service.CartItemService;
import com.edu.mum.util.SessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartItemServiceImpl implements CartItemService {
    @Autowired
    CartItemRepository cartItemRepository;

    @Autowired
    private SessionUtils sessionUtils;

    @Override
    public void save(CartItem cartItem) {
        cartItemRepository.save(cartItem);
    }

    @Override
    public List<CartItem> findAllByUser() {
        return cartItemRepository.findByUser_Id(sessionUtils.getCurrentUser().getId());
    }

    @Override
    public void delete(CartItem cartItem) {
        cartItemRepository.delete(cartItem);
    }

    @Override
    public Double getSubTotal() {
        return this.findAllByUser()
                .stream()
                .map(x -> x.getProduct().getPrice() * x.getQuantity())
                .reduce(0.0, Double::sum);
    }

    @Override
    public int getNumberOfProducts() {
        return this.findAllByUser().size();
    }
}
