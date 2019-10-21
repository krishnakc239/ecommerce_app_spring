package com.edu.mum.controller;

import com.edu.mum.domain.Product;
import com.edu.mum.domain.Review;
import com.edu.mum.domain.User;
import com.edu.mum.service.ProductService;
import com.edu.mum.service.ReviewService;
import com.edu.mum.service.UserService;
import com.edu.mum.util.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @PostMapping(value = "/review/create")
    public String saveReview(@Valid @ModelAttribute("review") Review review, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) throws IOException {
        if (bindingResult.hasErrors()) {
            Optional<Product> productGet = this.productService.findById(review.getProduct().getId());
            Product product = productGet.get();
            Optional<List<Review>> reviews = reviewService.findAllByProduct(product);
            model.addAttribute("reviews", reviews.get());
            model.addAttribute("product", product);
            Review r = new Review(new Product(review.getProduct().getId()));
            model.addAttribute("review", r);
            return "product/single-product";
        } else {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Optional<User> user = this.userService.findByUsername(auth.getName());
            System.out.println(user.get().getRole().getRole());
            if (!user.get().getRole().getRole().equals("ROLE_BUYER")) {
                redirectAttributes.addFlashAttribute("errorMessage", "Only buyer can review");
                return "redirect:/productDetails/" + review.getProduct().getId();
            }
            if (!user.isPresent()) {
                bindingResult.rejectValue("user", "error.user", "user cannot be null");
            } else {
                review.setUser(user.get());
            }
            review.setReviewDate(new Date());
            this.reviewService.create(review);
            redirectAttributes.addFlashAttribute("successMessage", "Review has been added");
        }
        return "redirect:/productDetails/" + review.getProduct().getId();
    }

    @GetMapping("/reviewList")
    public String reviewList(@RequestParam(defaultValue = "0") int page, Model model, Principal principal) {
        Page<Review> reviews = this.reviewService.findAllReviews(page);
        Pager pager = new Pager(reviews);
        model.addAttribute("pager", pager);

        //store current logged in user in "user" object
        UserDetails userDetails = (UserDetails) ((Authentication) principal).getPrincipal();
        Optional<User> user = userService.findByUsername(userDetails.getUsername());
        model.addAttribute("user", user.get());
        return "review/reviewList";
    }

    @GetMapping("/searchReview")
    public String searchPost(@RequestParam(defaultValue = "0") int page,
                             @RequestParam("searchParameter") String searchParameter, Model model) {
        Page<Review> reviews = reviewService.findAllByReviewMessageContainingIgnoreCaseOrProduct_NameContainingIgnoreCase(searchParameter, searchParameter, page);
        Pager pager = new Pager(reviews);
        model.addAttribute("pager", pager);
        return "review/reviewList";
    }

    @GetMapping("review/approve/{id}")
    public String approveSellerForAddProduct(@PathVariable Long id) {
        Review review = reviewService.findById(id);
        review.setApprove(true);
        reviewService.save(review);
        return "redirect:/reviewList";
    }
}
