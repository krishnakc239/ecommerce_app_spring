package com.edu.mum.controller;

import com.edu.mum.domain.User;
import com.edu.mum.service.ProductService;
import com.edu.mum.service.UserService;
import com.edu.mum.util.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {


    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;


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
    public String delete(@PathVariable("id")Long id) {
        User user = userService.findById(id);
        userService.deleteById(id);
        if (user.getRole().getRole().equals("ROLE_SELLER"))
            return "redirect:/sellerList";
        else
            return "redirect:/buyerList";
    }

    @GetMapping("/user/edit/{id}")
    public String edit(@PathVariable("id")Long id, Model model) {
        User user = userService.findById(id);
        if(user == null) {
            return "redirect:/users";
        }
        model.addAttribute("user", user);
        return "user/edit";
    }

    @PostMapping("/user/edit")
    public String edit(@ModelAttribute User user, Model model) {
        System.out.println("user goig to be update "+ user);
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
    public String approveSellerForAddProduct(@PathVariable Long id){
        User seller = userService.findById(id);
        seller.setStatus(true);
        userService.save(seller);
        return "redirect:/sellerList";
    }



}
