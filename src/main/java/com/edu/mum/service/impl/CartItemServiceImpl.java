package com.edu.mum.service.impl;

import com.edu.mum.domain.CartItem;
import com.edu.mum.domain.User;
import com.edu.mum.repository.CartItemRepository;
import com.edu.mum.service.CartItemService;
import com.edu.mum.util.SessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        if (SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser")){
            return 0.00;
        }
        return this.findAllByUserAndDelivered(sessionUtils.getCurrentUser(),false)
                .stream()
                .map(x -> x.getProduct().getPrice() * x.getQuantity())
                .reduce(0.0, Double::sum);
    }

    @Override
    public int getNumberOfProductsByUser() {
        if (SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser")){
            return 0;
        }
        return this.findAllByUserAndDelivered(sessionUtils.getCurrentUser(),false).size();
    }

    public void deleteById(Long aLong) {
        cartItemRepository.deleteById(aLong);
    }

    public Optional<CartItem> findById(Long aLong) {
        return cartItemRepository.findById(aLong);
    }


    @Override
    public List<CartItem> findAllByUserAndDelivered(User user, boolean delevered) {
        if (SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser")){
            return null;
        }
        return cartItemRepository.findAllByUserAndDelevered(user,delevered);
    }
}
