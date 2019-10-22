package com.edu.mum.controller;

import com.edu.mum.domain.CartItem;
import com.edu.mum.domain.Order;
import com.edu.mum.domain.Product;
import com.edu.mum.domain.ShipCountry;
import com.edu.mum.service.CartItemService;
import com.edu.mum.service.ProductService;
import com.edu.mum.util.SessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller
//@SessionAttributes("cartList")
public class CartController {

    Map<String, ShipCountry> shipCountries = new HashMap<>();
    @Autowired
    private SessionUtils sessionUtils;
    @Autowired
    private CartItemService cartItemService;
    @Autowired
    private ProductService productService;
    Double shippingCost = 0.0;

    @GetMapping("/addToCart/{pid}")
    public String addToCart(
            @PathVariable Integer pid,
            @RequestParam(defaultValue = "1") String orderQuantity,
            Model model) {
        Optional<Product> product = productService.findById(Long.valueOf(pid));
        Product addedProduct = product.isPresent() ? product.get() : new Product();
        model.addAttribute("product", addedProduct);
        CartItem cartItem = new CartItem();
        cartItem.setProduct(addedProduct);
        cartItem.setQuantity(Integer.parseInt(orderQuantity));
        cartItem.setStock(addedProduct.getQuantity());
        cartItem.setUser(sessionUtils.getCurrentUser());

        cartItemService.save(cartItem);
        //model.addAttribute("cartList", cartItemService.findAllByUser());
        return "redirect:/shoppingCart";
    }

    @GetMapping("/shoppingCart")
    public String productAdded(Model model, @ModelAttribute(name = "country") ShipCountry country) {
        model.addAttribute("cartList", cartItemService.findAllByUser());
        model.addAttribute("numberOfProducts", cartItemService.getNumberOfProducts());
        model.addAttribute("subtotal", cartItemService.getSubTotal());
        model.addAttribute("shipping", shippingCost);
        model.addAttribute("total", cartItemService.getSubTotal() + shippingCost);
        return "product/cart";
    }

    @GetMapping("/checkout")
    public String checkout(Model model){
        Order order = new Order();
        order.setUser(sessionUtils.getCurrentUser());
        model.addAttribute("order",order);
        return "product/checkout";
    }

    @GetMapping("/shop")
    public String shop() {
        return "product/shop";
    }

    /*
        @ModelAttribute(name = "subtotal")
        public double getTotalAmount() {
            return cartItemService.getTotalAmount();
        }
    */
    @ModelAttribute(name = "numberOfProducts")
    public int getNumberOfProducts() {
        return cartItemService.getNumberOfProducts();
    }

    @ModelAttribute("shipCountries")
    public Map shipCountries() {
        shipCountries.clear();
        shipCountries.put("NP", new ShipCountry("NP", "Nepal", 1d));
        shipCountries.put("TR", new ShipCountry("TR", "Turkey", 16d));
        shipCountries.put("UK", new ShipCountry("UK", "United Kingdom", 12d));
        shipCountries.put("US", new ShipCountry("US", "United States", 0d));
        shipCountries.put("CA", new ShipCountry("CA", "Canada", 5d));
        shipCountries.put("CN", new ShipCountry("CN", "China", 19d));
        shipCountries.put("BD", new ShipCountry("BD", "Bangladesh", 100d));
        shipCountries.put("MA", new ShipCountry("MA", "Morocco", 10d));


        return shipCountries;
    }

    @PostMapping("/calcShipping")
    public String getShippingCost(@ModelAttribute ShipCountry country) {
        System.out.println(country);
        shippingCost = shipCountries.get(country.getCountryCode()).getCost();
        return "redirect:/shoppingCart";
    }




}
