package com.edu.mum.repository;

import com.edu.mum.domain.Order;
import com.edu.mum.domain.User;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    Order findByUserAndPaid(User user, boolean paid);
}
