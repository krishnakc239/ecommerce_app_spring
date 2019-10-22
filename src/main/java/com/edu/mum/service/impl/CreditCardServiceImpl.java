package com.edu.mum.service.impl;

import com.edu.mum.domain.CreditCardInfo;
import com.edu.mum.repository.CreditCardRepository;
import com.edu.mum.service.CreditCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CreditCardServiceImpl implements CreditCardService {

    @Autowired
    private CreditCardRepository creditCardRepository;

    @Override
    public void updateAmount(CreditCardInfo creditCard) {
        CreditCardInfo prevCredCardInfo = creditCardRepository.findByCardNumber(creditCard.getCardNumber()).get();
        Double prevAmt = prevCredCardInfo.getAmount();
        Double diductAmt = creditCard.getAmount();
        prevCredCardInfo.setAmount(prevAmt-diductAmt);
        creditCardRepository.save(prevCredCardInfo);
        }

    @Override
    public Optional<CreditCardInfo> findById(Long id) {

        return creditCardRepository.findById(id);
    }

    @Override
    public Optional<CreditCardInfo> findByCardNumber(String number) {
        return creditCardRepository.findByCardNumber(number);
    }
}
