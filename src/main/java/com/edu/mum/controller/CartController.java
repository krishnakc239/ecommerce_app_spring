package com.edu.mum.controller;

import com.edu.mum.domain.*;
import com.edu.mum.service.*;
import com.edu.mum.util.Pager;
import com.edu.mum.util.SessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    @Autowired
    private OrderService orderService;
    @Autowired
    private CreditCardService creditCardService;
    @Autowired
    private UserService userService;

    Map<String, ShipCountry> shipCountries = new HashMap<>();
    Double shippingCost = 0.0;
    Double rewardsUsed = 0.0;

    @ModelAttribute(name = "subtotal")
    public double getTotalAmount() {
        return cartItemService.getSubTotal();
    }

    @ModelAttribute(name = "numberOfProducts")
    public int getNumberOfProducts() {
        return cartItemService.getNumberOfProductsByUser();
    }

    @ModelAttribute(name = "cartList")
    public List<CartItem> getCartItemList() {
        return cartItemService.findAllByUserAndDelivered(sessionUtils.getCurrentUser(), false);
    }

    @GetMapping("/addToCart/{pid}")
    public String addToCart(@PathVariable Long pid) {
        return addProductToCart(pid, 1);
    }

    @PostMapping("/addToCart/{pid}")
    public String addToCart(@PathVariable Long pid, @ModelAttribute(name = "simpleBean") SimpleBean simpleBean) {
        System.out.println(simpleBean);
        int quantity = simpleBean.getIntValue();
        return addProductToCart(pid, quantity);
    }

    @GetMapping("/shoppingCart")
    public String productAdded(Model model, @ModelAttribute(name = "country") ShipCountry country) {
        model.addAttribute("shipping", shippingCost);
        model.addAttribute("rewards", rewardsUsed);
        model.addAttribute("total", cartItemService.getSubTotal() + shippingCost - rewardsUsed);
        return "product/cart";
    }

    @GetMapping("/checkout")
    public String checkout(Model model) {
        Order order = new Order();
        order.setUser(sessionUtils.getCurrentUser());
        model.addAttribute("order", order);
        return "product/checkout";
    }

    @GetMapping("/shop")
    public String shop(@RequestParam(defaultValue = "0") int page, Model model) {
        Page<Product> productList = productService.findAllProducts(page);
        Pager pager = new Pager(productList);
        model.addAttribute("pager", pager);
        return "product/shop";
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

        Optional<CartItem> o = cartItemService.findById(iid);
        if (o.isPresent()) {
            CartItem c = o.get();
            Product p = productService.findById(c.getProduct().getId()).get();
            p.setStock(p.getStock() + c.getQuantity());
            cartItemService.deleteById(iid);
            productService.save(p);
        }

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
    public String getPaymentPage(@Valid @ModelAttribute("order") Order order, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("order", order);
            return "product/checkout";
        }
        order.setUser(sessionUtils.getCurrentUser());
        order.setSubTotal(cartItemService.getSubTotal());
        order.setShipping(shippingCost);
        order.setRewardsUsed(rewardsUsed);
        order.setTotalAmount(cartItemService.getSubTotal() + shippingCost - rewardsUsed);
        order.setStatus("Ordered");
        orderService.save(order);
        redirectAttributes.addFlashAttribute("order", order);
        return "redirect:/payment";
    }

    @GetMapping("/payment")
    public String showPaymentForm(@ModelAttribute CreditCardInfo credit, Model model) {
        model.addAttribute("shipping", shippingCost);
        model.addAttribute("rewards", rewardsUsed);
        model.addAttribute("total", cartItemService.getSubTotal() + shippingCost - rewardsUsed);
        model.addAttribute("credit", new CreditCardInfo());
        return "product/payment";
    }

    @PostMapping("/pay/{oid}")
    public String pay(@Valid @ModelAttribute("credit") CreditCardInfo credit,
                      BindingResult bindingResult, @PathVariable Long oid, Model model, HttpSession session) {
        Order order = orderService.findById(oid);
        if (bindingResult.hasErrors()) {
            model.addAttribute("shipping", shippingCost);
            model.addAttribute("total", cartItemService.getSubTotal() + shippingCost);
            model.addAttribute("credit", credit);
            model.addAttribute("order", order);
            return "product/payment";
        } else {
            Optional<CreditCardInfo> creditCard = creditCardService.findByCardNumber(credit.getCardNumber());
            if (creditCard.isPresent()) {
                creditCardService.updateAmount(credit);
                order.setPaid(true);
                updateCartItemsByUser();
                updateUserRewardPoint(order, session);
                model.addAttribute("order", order);
                model.addAttribute("subtotal", cartItemService.getSubTotal());
                model.addAttribute("numberOfProducts", cartItemService.getNumberOfProductsByUser());
                model.addAttribute("message", "Congratulation !! Your order is placed and ready to be shipped and deliver within 3 business days");
                return "product/orderSummary";
            } else {
                model.addAttribute("message", "This credit card is not valid");
                model.addAttribute("order", order);
                return "product/payment";
            }
        }
    }

    private void updateUserRewardPoint(Order order, HttpSession session) {
        double totalAmt = order.getTotalAmount();
        double prevPoint = order.getUser().getPoints();
        double rewardPoint = 0.00;
        if (totalAmt < 10) {
            rewardPoint = 1;
        } else if (totalAmt >= 10 && totalAmt < 100) {
            rewardPoint = 5;
        } else if (totalAmt >= 100 && totalAmt < 500) {
            rewardPoint = 10;
        } else if (totalAmt >= 500 && totalAmt < 1000) {
            rewardPoint = 15;
        } else {
            rewardPoint = 20;
        }
        prevPoint += rewardPoint;
        order.getUser().setPoints(prevPoint);
        //update reward points in session user
        User currentuser = sessionUtils.getCurrentUser();
        currentuser.setPoints(prevPoint - rewardsUsed);
        rewardsUsed = 0.0;
        session.setAttribute("loggedInUser", currentuser);
        orderService.save(order);
        //update user rewards
    }

    private void updateCartItemsByUser() {
        User user = sessionUtils.getCurrentUser();
        List<CartItem> cartItems = cartItemService.findAllByUserAndDelivered(user, false);
        for (CartItem c : cartItems) {
            c.setDelevered(true);
            cartItemService.save(c);
        }
    }

    String addProductToCart(Long pid, int quantity) {
        Optional<Product> product = productService.findById(pid);
        if (product.isPresent()) {
            boolean cartItemUpdated = false;//update current item in cart
            for (CartItem i : cartItemService.findAllByUserAndDelivered(sessionUtils.getCurrentUser(), false)) {
                if (i.getProduct().getId() == pid) {
                    Product p = product.get();
                    if (p.getStock() >= quantity) {
                        p.setStock(p.getStock() - quantity);//decrease stock of product
                        i.setQuantity(i.getQuantity() + quantity);
                        cartItemService.save(i);
                    }
                    cartItemUpdated = true;
                }
            }
            if (!cartItemUpdated) {
                Product p = product.get();
                if (p.getStock() >= quantity) {
                    p.setStock(p.getStock() - quantity);//decrease stock of product
                    CartItem cartItem = new CartItem();
                    cartItem.setProduct(p);
                    cartItem.setQuantity(quantity);
                    cartItem.setUser(sessionUtils.getCurrentUser());
                    productService.save(p);
                    cartItemService.save(cartItem);
                } else {
                    //out of stock
                    return "/";
                }
            }
        }

        return "redirect:/shoppingCart";
    }

    @PostMapping("/useRewards")
    public String useRewards() {
        System.out.println("useRewards");
        rewardsUsed = sessionUtils.getCurrentUser().getPoints();
        return "redirect:/shoppingCart";
    }

}
