package com.edu.mum.controller;

import com.edu.mum.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class DashboardController {

    @Autowired
    private CartItemService cartItemService;

    @GetMapping("/dashboard")
    public String showDashboard(){
        return "home/dashboard";
    }

    @ModelAttribute(name = "subtotal")
    public double getTotalAmount() {
        return cartItemService.getSubTotal();
    }

    @ModelAttribute(name = "numberOfProducts")
    public int getNumberOfProducts() {
        return cartItemService.getNumberOfProductsByUser();
    }
}
