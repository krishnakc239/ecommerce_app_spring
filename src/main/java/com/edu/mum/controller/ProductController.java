package com.edu.mum.controller;

import com.edu.mum.domain.Product;
import com.edu.mum.domain.Review;
import com.edu.mum.domain.User;
import com.edu.mum.service.CategoryService;
import com.edu.mum.service.ProductService;
import com.edu.mum.service.ReviewService;
import com.edu.mum.service.UserService;
import com.edu.mum.util.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
public class ProductController {

    private final static String upload_dir = System.getProperty("user.dir") + "/src/main/resources/static/uploads/images";

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ReviewService reviewService;

    @GetMapping("/product/create")
    public String create(@ModelAttribute Product product, Model model) {
        model.addAttribute("categories", categoryService.getAllCategory());
        return "product/addProduct";
    }

    @PostMapping(value = "/product/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String index(@Valid @ModelAttribute Product product, @RequestParam("file") MultipartFile imgFile
            , BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) throws IOException {
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

    @GetMapping("/productList")
    public String index(@RequestParam(defaultValue = "0") int page, Model model, Principal principal) {
        Page<Product> products = this.productService.findAllProducts(page);
        Pager pager = new Pager(products);
        model.addAttribute("pager", pager);

        //store current logged in user in "user" object
        UserDetails userDetails = (UserDetails) ((Authentication) principal).getPrincipal();
        Optional<User> user = userService.findByUsername(userDetails.getUsername());
        model.addAttribute("user", user.get());
        return "product/productList";
    }

    @RequestMapping("/product/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        System.out.println("product to be deleted =" + id);
        Optional<Product> product = this.productService.findById(id);
        productService.delete(product.get());
        return "redirect:/productList";
    }

    /**
     * Display for to edit a product
     *
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/product/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model) {
        Optional<Product> product = productService.findById(id);
        if (!product.isPresent()) {
            model.addAttribute("message", "product not found");
            return "redirect:product/productList";
        }
        Product productToEdit = product.get();
        model.addAttribute("categories", categoryService.getAllCategory());
//        System.out.println("pst to be edited :"+ product1.getUser().getId());
        model.addAttribute("product", productToEdit);
        return "product/editProduct";
    }

    @PostMapping(value = "/product/edit", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ModelAndView edit(@Valid @ModelAttribute Product product, BindingResult bindingResult,
                             @RequestParam("file") MultipartFile imgFile) throws IOException {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/product/editProduct");
        // Perform validation
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("product/editProduct");
            return modelAndView;
        }

        if (!imgFile.getOriginalFilename().isEmpty()) {
            product.setCoverImage(imgFile.getOriginalFilename());
            Path fileNameAndPath = Paths.get(upload_dir, imgFile.getOriginalFilename());
            Files.write(fileNameAndPath, imgFile.getBytes());
        }
        this.productService.create(product);
        modelAndView.addObject("successMessage", "Product has been updated");
        modelAndView.addObject("product", product);
        return modelAndView;
    }


    @GetMapping("/searchProduct")
    public String searchPost(@RequestParam(defaultValue = "0") int page,
                             @RequestParam("searchParameter") String searchParameter, Model model) {
        Page<Product> products = productService.findAllByNameContainingIgnoreCaseOrCategory_CategoryNameContainingIgnoreCase(searchParameter, searchParameter, page);
        Pager pager = new Pager(products);
        model.addAttribute("pager", pager);
//        model.addAttribute("avgRatingMap", ArithmeticUtils.getAvgRatingMap(productService.findAll()));
        return "product/productList";
    }

    @GetMapping("/productDetails/{id}")
    public String productDetails(@PathVariable("id") Long product_id, Model model) {
        Optional<Product> productGet = this.productService.findById(product_id);
        Product product = productGet.get();
        Optional<List<Review>> reviews = reviewService.findAllByProduct(product);
        if (reviews.isPresent())
            model.addAttribute("reviews", reviews.get());
        else model.addAttribute("reviews", null);
        model.addAttribute("product", product);
        Review r = new Review(new Product(product_id));
        model.addAttribute("review", r);
        return "product/single-product";

    }


}
