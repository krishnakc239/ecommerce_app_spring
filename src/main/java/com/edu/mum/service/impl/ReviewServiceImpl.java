package com.edu.mum.service.impl;

import com.edu.mum.domain.Product;
import com.edu.mum.domain.Review;
import com.edu.mum.repository.CategoryRepository;
import com.edu.mum.repository.ReviewRepository;
import com.edu.mum.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewServiceImpl implements ReviewService {
    private ReviewRepository reviewRepository;
    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository){
        this.reviewRepository = reviewRepository;
    }

    @Override
    public Optional<List<Review>> findAllByProduct(Product product) {
        return reviewRepository.findAllByProduct(product);
    }
}
