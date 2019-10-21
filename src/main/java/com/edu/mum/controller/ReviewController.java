package com.edu.mum.controller;

import com.edu.mum.domain.Product;
import com.edu.mum.domain.User;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

public class ReviewController {

    @PostMapping(value = "/review/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String index(@Valid @ModelAttribute Product product, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) throws IOException {
        if (bindingResult.hasErrors()) {
            model.addAttribute("successMessage", "There is problem in product form");
            model.addAttribute("categories", categoryService.getAllCategory());
            return "product/addProduct";
        } else {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Optional<User> user = this.userService.findByUsername(auth.getName());
            if (!user.isPresent()) {
                model.addAttribute("categories", categoryService.getAllCategory());
                bindingResult.rejectValue("user", "error.product", "seller cannot be null");
            } else {
                product.setUser(user.get());
            }
            List<Product> savedProduct = productService.findAllByCode(product.getCode());
            if (savedProduct.size() > 1) {
                product.updateQuantity();
            }
            if (!imgFile.getOriginalFilename().isEmpty()) {
                Path fileNameAndPath = Paths.get(upload_dir, imgFile.getOriginalFilename());
                Files.write(fileNameAndPath, imgFile.getBytes());
                product.setCoverImage(imgFile.getOriginalFilename());
            }
            this.productService.create(product);
            redirectAttributes.addFlashAttribute("successMessage", "Product has been added");

        }
        return "redirect:/productList";
    }
}
