package com.edu.mum.domain;

import org.hibernate.validator.constraints.CreditCardNumber;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "credit")
public class CreditCardInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Length(min = 16, max = 16, message = "Card number should be of 16 length")
    @NotBlank
    private String cardNumber;
    @NotBlank(message = "expiry date can not be null")
    private String expDate;
    @NotBlank(message = "cv code can not be null")
    private String cvCode;
    @NotNull(message = "please fill the amount value")
    private double amount;

    public CreditCardInfo() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getExpDate() {
        return expDate;
    }

    public void setExpDate(String expDate) {
        this.expDate = expDate;
    }

    public String getCvCode() {
        return cvCode;
    }

    public void setCvCode(String cvCode) {
        this.cvCode = cvCode;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "CreditCardInfo{" +
                "id=" + id +
                ", cardNumber='" + cardNumber + '\'' +
                ", expDate='" + expDate + '\'' +
                ", cvCode='" + cvCode + '\'' +
                ", amount=" + amount +
                '}';
    }
}
