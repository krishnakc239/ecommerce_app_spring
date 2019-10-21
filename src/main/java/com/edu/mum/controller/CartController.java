package com.edu.mum.controller;

import com.edu.mum.domain.CartItem;
import com.edu.mum.domain.Product;
import com.edu.mum.service.CartItemService;
import com.edu.mum.service.ProductService;
import com.edu.mum.util.SessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
//@SessionAttributes("cartList")
public class CartController {

    @Autowired
    private SessionUtils sessionUtils;

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private ProductService productService;

    @GetMapping("/addToCart/{pid}")
    public String addToCart(
                            @PathVariable Integer pid,
                            @RequestParam(defaultValue = "1") String orderQuantity,
                            Model model){
        Optional<Product> product = productService.findById(Long.valueOf(pid));
        Product addedProduct = product.isPresent()? product.get(): new Product();
        model.addAttribute("product",addedProduct);
        CartItem cartItem = new CartItem();
        cartItem.setProduct(addedProduct);
        cartItem.setQuantity(Integer.parseInt(orderQuantity));
        cartItem.setStock(addedProduct.getQuantity());
        cartItem.setUser(sessionUtils.getCurrentUser());

        cartItemService.save(cartItem);
        model.addAttribute("cartList", cartItemService.findAll());
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
