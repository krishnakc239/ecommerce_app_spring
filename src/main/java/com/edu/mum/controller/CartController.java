package com.edu.mum.controller;

import com.edu.mum.domain.*;
import com.edu.mum.service.CartItemService;
import com.edu.mum.service.CreditCardService;
import com.edu.mum.service.OrderService;
import com.edu.mum.service.ProductService;
import com.edu.mum.util.SessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
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

    @Autowired
    private OrderService orderService;
    @Autowired
    private CreditCardService creditCardService;
    Double shippingCost = 0.0;

    @GetMapping("/addToCart/{pid}")
    public String addToCart(@PathVariable Long pid) {
        Optional<Product> product = productService.findById(pid);
        if (product.isPresent()) {
            Product p = product.get();
            if (p.getStock() > 0) {
                p.setStock(p.getStock() - 1);//decrease stock of product
                CartItem cartItem = new CartItem();
                cartItem.setProduct(p);
                cartItem.setQuantity(1);
                cartItem.setUser(sessionUtils.getCurrentUser());
                productService.save(p);
                cartItemService.save(cartItem);
            } else {
                //out of stock
            }
        }

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
        System.out.println("/calcShipping:" + country);
        shippingCost = shipCountries.get(country.getCountryCode()).getCost();
        return "redirect:/shoppingCart";
    }

    @GetMapping("/removeItem/{iid}")
    public String removeItem(@PathVariable Long iid) {
        System.out.println("/removeItem iid:" + iid);
        cartItemService.deleteById(iid);
        return "redirect:/shoppingCart";
    }

    @GetMapping("/incQuantity/{iid}")
    public String incQuantity(@PathVariable Long iid) {
        System.out.println("/incQuantity iid:" + iid);
        Optional<CartItem> o = cartItemService.findById(iid);
        if (o.isPresent()) {
            CartItem c = o.get();
            if (c.getProduct().getStock() > 0) {
                Product p = productService.findById(c.getProduct().getId()).get();
                p.setStock(p.getStock() - 1);
                c.setQuantity(c.getQuantity() + 1);
                cartItemService.save(c);
                productService.save(p);
            } else {
                //product not in stock
            }
        }

        return "redirect:/shoppingCart";
    }

    @GetMapping("/decQuantity/{iid}")
    public String decQuantity(@PathVariable Long iid) {
        System.out.println("/decQuantity iid:" + iid);
        Optional<CartItem> o = cartItemService.findById(iid);
        if (o.isPresent()) {
            CartItem c = o.get();
            if (c.getQuantity() == 1)
                cartItemService.delete(c);
            else {
                c.setQuantity(c.getQuantity() - 1);
                cartItemService.save(c);
            }
            Product p = productService.findById(c.getProduct().getId()).get();
            p.setStock(p.getStock() + 1);
            productService.save(p);
        }
        return "redirect:/shoppingCart";
    }


    @PostMapping("/placeOrder")
    public String getPaymentPage(@ModelAttribute Order order,RedirectAttributes redirectAttributes){
        order.setUser(sessionUtils.getCurrentUser());
//        order.setCartItemList(cartItemService.findAllByUser());
        order.setTotalAmount(cartItemService.getSubTotal() + shippingCost);
        orderService.save(order);
        redirectAttributes.addFlashAttribute("order",order);
        return "redirect:/payment";
    }

    @GetMapping("/payment")
    public String  showPaymentForm(@ModelAttribute CreditCardInfo credit, Model model){
        model.addAttribute("subtotal", cartItemService.getSubTotal());
        model.addAttribute("shipping", shippingCost);
//        model.addAttribute("order", model.asMap().get("order"));
        model.addAttribute("total", cartItemService.getSubTotal() + shippingCost);
        model.addAttribute("credit",new CreditCardInfo());
        return "product/payment";
    }
    @PostMapping("/pay/{oid}")
    public String pay(@Valid @ModelAttribute CreditCardInfo credit,
                      BindingResult bindingResult,@PathVariable Long oid, Model model){
        Order order = orderService.findById(oid);
        System.out.println(credit);
        if (bindingResult.hasErrors()){
            model.addAttribute("order",order);
            System.out.println(bindingResult.getAllErrors());
            return "product/payment";
        }else {
            Optional<CreditCardInfo> creditCard = creditCardService.findByCardNumber(credit.getCardNumber());
            if (creditCard.isPresent()){
                creditCardService.updateAmount(credit);
                order.setPaid(true);
                model.addAttribute("order",order);
                model.addAttribute("message","Congratulation !! Your order is placed and ready to be shipped");
                return "product/orderSummary";
            }else {
                model.addAttribute("message","This credit card is not valid");
                model.addAttribute("order",order);
                return "product/payment";
            }
        }
    }



}
