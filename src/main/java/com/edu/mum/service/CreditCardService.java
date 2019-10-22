package com.edu.mum.service;

import com.edu.mum.domain.CreditCardInfo;

import java.util.Optional;

public interface CreditCardService {
    void updateAmount(CreditCardInfo creditCardInfo);
    Optional<CreditCardInfo> findById(Long id);
    Optional<CreditCardInfo> findByCardNumber(String number);
}
