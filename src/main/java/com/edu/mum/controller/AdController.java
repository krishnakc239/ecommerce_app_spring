package com.edu.mum.controller;

import com.edu.mum.domain.Ad;
import com.edu.mum.domain.Product;
import com.edu.mum.domain.User;
import com.edu.mum.service.AdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
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
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
public class AdController {

    private final static String upload_dir = System.getProperty("user.dir") + "/src/main/resources/static/uploads/images";

    @Autowired
    AdService adService;


        @GetMapping("/addAdvertisement")
        public String adsForm(Model model) {
            model.addAttribute("ad", new Ad());
            return "home/addAdvertisement";
        }


    @PostMapping(value = "/addAdvertisement", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String adsFromSubmit(@Valid @ModelAttribute Ad ad,BindingResult bindingResult ,@RequestParam("file") MultipartFile imgFile) throws IOException{

        if (bindingResult.hasErrors()) {
            return "home/addAdvertisement";
        } else {

            if (!imgFile.getOriginalFilename().isEmpty()) {
                Path fileNameAndPath = Paths.get(upload_dir, imgFile.getOriginalFilename());
                Files.write(fileNameAndPath, imgFile.getBytes());
                ad.setImage(imgFile.getOriginalFilename());
            }
            this.adService.create(ad);
        }
            return "redirect:/";
    }

}
