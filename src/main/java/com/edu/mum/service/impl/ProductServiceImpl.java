package com.edu.mum.service.impl;

import com.edu.mum.domain.Product;
import com.edu.mum.repository.ProductRepository;
import com.edu.mum.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public  class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product create(Product product) {
        return productRepository.save(product);
    }

    @Override
    public void delete(Product product) {
        productRepository.delete(product);
    }

    @Override
    public Page<Product> findAllProducts(int page) {
        return productRepository.findAll(new PageRequest(subtractPageByOne(page),6));
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public List<Product> findAllByCode(String code) {
        return productRepository.findAllByCode(code);
    }

    @Override
    public List<Product> findTop5ByName(int page) {
        return productRepository.findTop5ByName(new PageRequest(subtractPageByOne(page),5));
    }

    @Override
    public Page<Product> findAllByNameContainingIgnoreCaseOrCategory_CategoryNameContainingIgnoreCase(String name, String category, int page) {
        return productRepository.findAllByNameContainingIgnoreCaseOrCategory_CategoryNameContainingIgnoreCase(name,category,new PageRequest(subtractPageByOne(page), 5));
    }

    private int subtractPageByOne(int page) {
        return (page < 1) ? 0 : page - 1;
    }
}
