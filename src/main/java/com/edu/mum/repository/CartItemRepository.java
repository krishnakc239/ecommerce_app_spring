package com.edu.mum.repository;

import com.edu.mum.domain.CartItem;
import com.edu.mum.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {


    public List<CartItem> findByUser_Id(Long id);

    @Override
    void deleteById(Long aLong);

    @Override
    Optional<CartItem> findById(Long aLong);

    List<CartItem> findAllByUserAndDelevered(User user, boolean delevered);
}
