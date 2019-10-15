package com.edu.mum.controller;

import com.edu.mum.domain.Product;
import com.edu.mum.domain.User;
import com.edu.mum.service.CategoryService;
import com.edu.mum.service.ProductService;
import com.edu.mum.service.UserService;
import com.edu.mum.util.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
import java.util.List;
import java.util.Optional;

@Controller
public class ProductController {

    private final static String upload_dir= System.getProperty("user.dir")+"/src/main/resources/static/uploads/images";

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/create")
    public String create(@ModelAttribute Product product, Model model){
        model.addAttribute("categories",categoryService.getAllCategory());
        return "product/addProduct";
    }

    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String index(@Valid @ModelAttribute Product product, @RequestParam("file") MultipartFile imgFile
                                , BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) throws IOException {
        if (bindingResult.hasErrors()){
            model.addAttribute("successMessage", "There is problem creating this product");
            return "product/addProduct";
        }else {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Optional<User> user = this.userService.findByUsername(auth.getName());
            if( !user.isPresent() ){
                bindingResult.rejectValue("user", "error.product", "seller cannot be null");
            }
            List<Product> savedProduct = productService.findAllByCode(product.getCode());
            if (savedProduct.size()> 1){
                product.updateQuantity();
            }
            product.setCoverImage(imgFile.getOriginalFilename());
            Path fileNameAndPath = Paths.get(upload_dir,imgFile.getOriginalFilename());

            Files.write(fileNameAndPath,imgFile.getBytes());

            product.setUser(user.get());
            this.productService.create(product);
            redirectAttributes.addFlashAttribute("successMessage","Product has been added");

        }  return "redirect:/productList";
    }

    @GetMapping("/productList")
    public String index(@RequestParam(defaultValue = "0") int page, Model model){
        Page<Product> products = this.productService.findAllProducts(page);
        Pager pager = new Pager(products);
        model.addAttribute("pager", pager);
        return "product/productList";
    }

    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id){
        System.out.println("product to be deleted ="+id);
        Optional<Product> product = this.productService.findById(id);
//        if( !product.isPresent()){
//            notifyService.addErrorMessage("Cannot find product #" + id);
//        } else {
//            productService.deleteById(id);
            productService.delete(product.get());
            System.out.println("product delted");
//        }
        return "redirect:/productList";
    }
    /**
     * Display for to edit a product
     * @param id
     * @param model
     * @return
     */
    @GetMapping( "/edit/{id}" )
    public String edit(@PathVariable("id") Long id, Model model){
        Optional<Product> product = productService.findById(id);
        if( !product.isPresent()  ){
            model.addAttribute("message","product not found");
            return "redirect:product/productList";
        }
        Product productToEdit = product.get();
        model.addAttribute("categories",categoryService.getAllCategory());
//        System.out.println("pst to be edited :"+ product1.getUser().getId());
        model.addAttribute("product", productToEdit);
        return "product/editProduct";
    }

    @PostMapping(value = "/edit", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ModelAndView edit(@Valid @ModelAttribute Product product, BindingResult bindingResult,
                            @RequestParam("file") MultipartFile imgFile) throws IOException {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("views/products/edit");
        // Perform validation
        if (bindingResult.hasErrors()){
            modelAndView.setViewName("product/editProduct");
            return modelAndView;
        }
        User user = this.userService.findById(product.getUser().getId());
        if( user==null ){
            bindingResult.rejectValue("user", "error.product", "Author cannot be null");
        }

        if (imgFile!=null){
            System.out.println("new image goging to be updated!!");
            product.setCoverImage(imgFile.getOriginalFilename());
            Path fileNameAndPath = Paths.get(upload_dir,imgFile.getOriginalFilename());

            Files.write(fileNameAndPath,imgFile.getBytes());
        }
//        if( !bindingResult.hasErrors() ){
//            Optional<Product> productOptional = productService.findById(product.getId());
//            if (productOptional.isPresent()){
//                Product p = productOptional.get();
//                p.setUser(user);
//                p.setTitle(product.getTitle());
//                p.setCategory(product.getCategory());
//                p.setBody(product.getBody());
                this.productService.create(product);
                modelAndView.addObject("successMessage", "Product has been updated");
                modelAndView.addObject("product", product);
//            }
//            product.setUser(user);

//        }
        return modelAndView;
    }
    

    @GetMapping("/searchProduct")
    public String searchPost(@RequestParam(defaultValue = "0") int page,
                             @RequestParam("searchParameter") String searchParameter, Model model){
        Page<Product> products = productService.findAllByNameContainingIgnoreCaseOrCategory_CategoryNameContainingIgnoreCase(searchParameter,searchParameter,page);
        Pager pager = new Pager(products);
        model.addAttribute("pager", pager);
//        model.addAttribute("avgRatingMap", ArithmeticUtils.getAvgRatingMap(productService.findAll()));
        return "product/productList";
    }

}
