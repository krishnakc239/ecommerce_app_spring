package com.edu.mum.controller;

import com.edu.mum.domain.Cart;
import com.edu.mum.domain.Product;
import com.edu.mum.service.ProductService;
import com.edu.mum.util.SessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@SessionAttributes("cartList")
public class CartController {

    List<Cart> cartList = new ArrayList<>();
    @Autowired
    private SessionUtils sessionUtils;

    @Autowired
    private ProductService productService;

    @GetMapping("/addToCart/{pid}")
    public String addToCart(@ModelAttribute Cart cart,
                            @PathVariable Integer pid,
                            @RequestParam(defaultValue = "1") String orderQuantity,
                            Model model){
        Optional<Product> product = productService.findById(Long.valueOf(pid));
        Product addedProduct = product.isPresent()? product.get(): new Product();
        model.addAttribute("product",addedProduct);
        cart.setProduct(addedProduct);
        cart.setQuantity(Integer.parseInt(orderQuantity));
        cart.setStock(addedProduct.getQuantity());
        cartList.add(cart);
        model.addAttribute("cartList",cartList);
        return "product/cart";
    }

    @GetMapping("/checkout")
    public String checkout(){
        return "product/checkout";
    }

    @GetMapping("/shop")
    public String shop(){
        return "product/shop";
    }
}
