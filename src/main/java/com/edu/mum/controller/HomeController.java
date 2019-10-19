package com.edu.mum.controller;

import com.edu.mum.domain.Product;
import com.edu.mum.service.ProductService;
import com.edu.mum.util.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    @Autowired
    private ProductService productService;

    @GetMapping({"/","/index"})
    public String index1(@RequestParam(defaultValue = "0") int page, Model model){
        Page<Product> products = this.productService.findAllProducts(page);
        Pager pager = new Pager(products);
        model.addAttribute("pager", pager);
        return "home/index";
    }
}
