package com.edu.mum.service;

import com.edu.mum.domain.Product;
import com.edu.mum.domain.Review;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface ReviewService {

    Optional<List<Review>> findAllByProduct(Product product);
    Review findById(Long id);
    Review save(Review review);
    Review create(Review review);
    Page<Review> findAllByReviewMessageContainingIgnoreCaseOrProduct_NameContainingIgnoreCase(String message, String product, int page);
    Page<Review> findAllReviews(int page);
}
