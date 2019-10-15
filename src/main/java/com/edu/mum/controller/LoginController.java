package com.edu.mum.controller;

import com.edu.mum.domain.User;
import com.edu.mum.service.RoleService;
import com.edu.mum.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class LoginController {

    @Autowired
    private  UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleService roleService;

    @RequestMapping("/login")
    public String login(){
        // User doesn't need to re-enter credentials
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        System.out.println("auth =============="+ auth);
        if ( (auth instanceof AnonymousAuthenticationToken) ) {
            System.out.println("ins of anonymous user");
            return "login";
        } else {
            return "redirect:/";
        }
    }

    @GetMapping("/signup")
    public String signup( @ModelAttribute User user, Model model){
        System.out.println(roleService.findRoles());
        model.addAttribute("roles",roleService.findRoles());

        return "registeration";
    }

    @PostMapping("/register")
    public ModelAndView register(@Valid @ModelAttribute User user, BindingResult bindingResult){
        System.out.println("inside register method !!!!!!!!!!!");
        ModelAndView modelAndView = new ModelAndView();
        Optional<User> userExists = userService.findByEmail(user.getEmail());
        if (userExists.isPresent()) {
            System.out.println("user exis!!!!!");
            modelAndView.addObject("roles",roleService.findRoles());
            bindingResult
                    .rejectValue("email", "error.user",
                            "There is already a user registered with the email provided");
        }
        if (bindingResult.hasErrors()) {
            System.out.println("registraion form has errors !!!!!!!!!!!!!");
            modelAndView.addObject("roles",roleService.findRoles());
            modelAndView.setViewName("registeration");
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setActive(1);
            userService.create(user);
            System.out.println("user :"+user +" registered !!!!!");
            modelAndView.addObject("roles",roleService.findRoles());
            modelAndView.addObject("successMessage", "User has been registered successfully");
            modelAndView.addObject("user", new User());
            modelAndView.setViewName("registeration");

        }
        return modelAndView;
    }

}
