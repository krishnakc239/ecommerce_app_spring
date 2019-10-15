package com.edu.mum.service;

import com.edu.mum.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


public interface ProductService {
    Product create(Product product);
    void delete(Product product);
    Page<Product> findAllProducts(int page);

    Optional<Product> findById(Long id);
    List<Product> findAllByCode(String code);
    List<Product> findTop5ByName(int page);
    Page<Product> findAllByNameContainingIgnoreCaseOrCategory_CategoryNameContainingIgnoreCase(String name, String category, int page);

}
