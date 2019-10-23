package com.edu.mum.controller;

import com.edu.mum.domain.Product;
import com.edu.mum.service.AdService;
import com.edu.mum.service.CartItemService;
import com.edu.mum.service.ProductService;
import com.edu.mum.util.Pager;
import com.edu.mum.util.SessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class HomeController {

    @Autowired
    private ProductService productService;

    @Autowired
    private AdService adService;

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private SessionUtils sessionUtils;

    @GetMapping({"/","/index"})
    public String index1(@RequestParam(defaultValue = "0") int page, Model model, HttpSession session){
        Page<Product> products = this.productService.findAllProducts(page);
        Pager pager = new Pager(products);
        model.addAttribute("pager", pager);
        session.setAttribute("loggedInUser",sessionUtils.getCurrentUser());
        model.addAttribute("adsList",adService.findAllAds());
        return "home/index";
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
