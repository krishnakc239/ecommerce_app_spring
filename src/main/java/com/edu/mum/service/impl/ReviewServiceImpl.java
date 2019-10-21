package com.edu.mum.service.impl;

import com.edu.mum.domain.Product;
import com.edu.mum.domain.Review;
import com.edu.mum.repository.ReviewRepository;
import com.edu.mum.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    public Review create(Review review) {
        return reviewRepository.save(review);
    }

    @Override
    public Page<Review> findAllByReviewMessageContainingIgnoreCaseOrProduct_NameContainingIgnoreCase(String message, String product, int page) {
        return reviewRepository.findAllByReviewMessageContainingIgnoreCaseOrProduct_NameContainingIgnoreCase(message,product,new PageRequest(subtractPageByOne(page), 5));
    }

    @Override
    public Page<Review> findAllReviews(int page) {
        return reviewRepository.findAll(new PageRequest(subtractPageByOne(page),10));
    }

    @Override
    public Optional<List<Review>> findAllByProduct(Product product) {
        return reviewRepository.findAllByProduct(product);
    }

    @Override
    public Review findById(Long id) {
        return reviewRepository.getOne(id);
    }

    @Override
    public Review save(Review review) {
        return reviewRepository.save(review);
    }

    private int subtractPageByOne(int page) {
        return (page < 1) ? 0 : page - 1;
    }

}
