package com.edu.mum.repository;

import com.edu.mum.domain.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    public  List<CartItem> findAllByUser_id(Long id);
}
