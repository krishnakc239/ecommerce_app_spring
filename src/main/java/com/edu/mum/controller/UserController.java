package com.edu.mum.controller;

import com.edu.mum.domain.Order;
import com.edu.mum.domain.Product;
import com.edu.mum.domain.User;
import com.edu.mum.service.OrderService;
import com.edu.mum.service.ProductService;
import com.edu.mum.service.UserService;
import com.edu.mum.util.Pager;
import groovy.lang.GrabExclude;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
public class UserController {

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;


    @GetMapping("/sellerList")
    public String getSellers(@RequestParam(defaultValue = "0") int page, Model model) {
        Page<User> users = this.userService.findAllUsers(page);
        Pager pager = new Pager(users);
        model.addAttribute("pager", pager);
        return "user/sellerList";
    }

    @GetMapping("/buyerList")
    public String getbuyerList(@RequestParam(defaultValue = "0") int page, Model model) {
        Page<User> users = this.userService.findAllUsers(page);
        Pager pager = new Pager(users);
        model.addAttribute("pager", pager);
        return "user/buyerList";
    }

    @GetMapping("/user/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        User user = userService.findById(id);
        userService.deleteById(id);
        if (user.getRole().getRole().equals("ROLE_SELLER"))
            return "redirect:/sellerList";
        else
            return "redirect:/buyerList";
    }

    @GetMapping("/user/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model) {
        User user = userService.findById(id);
        if (user == null) {
            return "redirect:/users";
        }
        model.addAttribute("user", user);
        return "user/edit";
    }

    @PostMapping("/user/edit")
    public String edit(@ModelAttribute User user, Model model) {
        System.out.println("user goig to be update " + user);
        User updatedUser = userService.findById(user.getId());
        updatedUser.setFirstName(user.getFirstName());
        updatedUser.setLastName(user.getLastName());
        updatedUser.setEmail(user.getEmail());
        userService.save(updatedUser);
        model.addAttribute("successMessage", "User has been updated.");
        model.addAttribute("user", updatedUser);
        return "user/edit";
    }

    @GetMapping("user/approve/{id}")
    public String approveSellerForAddProduct(@PathVariable Long id) {
        User seller = userService.findById(id);
        seller.setStatus(true);
        userService.save(seller);
        return "redirect:/sellerList";
    }

    @GetMapping("/follow/{pid}/{fid}")
    public String updateFollower(
            @PathVariable Long pid,
            @PathVariable String fid
    ) {
        Optional<Product> product = productService.findById(pid);
        User seller = userService.findById(product.get().getUser().getId());
        Optional<User> follower = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        Set<User> followers = seller.getFollowings();
        if (fid.equals("1")) {
            followers.add(follower.get());
        } else {
            followers.remove(follower.get());
        }
        seller.setFollowings(followers);
        userService.save(seller);

        return "redirect:/productDetails/{pid}";
    }

    @GetMapping("/user/details")
    public String userDetails(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> user = this.userService.findByUsername(auth.getName());
        model.addAttribute("user", user.get());
        return "user/details";
    }

    @GetMapping("/orderList")
    public String OrderList(@RequestParam(defaultValue = "0") int page, Model model) {
        Optional<User> user = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        Page<Order> orderList = this.orderService.findAllByUser(user.get(), page);
        System.out.println("he he" + orderList);
        Pager pager = new Pager(orderList);
        model.addAttribute("pager", pager);
        return "user/orderList";
    }

}
