package com.edu.mum.repository;

import com.edu.mum.domain.Order;
import com.edu.mum.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    Order findByUserAndPaid(User user, boolean paid);
    Page<Order> findAllByUser(User user, Pageable pageable);
}
