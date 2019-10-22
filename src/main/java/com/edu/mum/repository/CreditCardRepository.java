package com.edu.mum.repository;

import com.edu.mum.domain.CreditCardInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CreditCardRepository extends JpaRepository<CreditCardInfo, Long> {

    Optional<CreditCardInfo> findByCardNumber(String number);

}
