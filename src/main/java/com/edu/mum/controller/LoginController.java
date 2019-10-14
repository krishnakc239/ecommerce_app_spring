package com.edu.mum.controller;

import com.edu.mum.domain.User;
import com.edu.mum.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

    @Autowired
    private RoleService roleService;

    @RequestMapping("/login")
    public String login(){
        // User doesn't need to re-enter credentials
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        System.out.println("auth =============="+ auth);
//        if ( (auth instanceof AnonymousAuthenticationToken) ) {
//            System.out.println("ins of anonymous user");
            return "login";
//        } else {
//            return "redirect:/";
//        }
    }

    @GetMapping("/signup")
    public String signup(@ModelAttribute User user, Model model){
        System.out.println(roleService.findRoles());
        model.addAttribute("roles",roleService.findRoles());

        return "registration";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute User user){
        return "redirect:/";
    }

}
