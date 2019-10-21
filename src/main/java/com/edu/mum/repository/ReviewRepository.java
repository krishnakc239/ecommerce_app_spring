package com.edu.mum.repository;

import com.edu.mum.domain.Product;
import com.edu.mum.domain.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    Optional<List<Review>> findAllByProduct(Product product);

    Page<Review> findAll(Pageable pageable);

    Page<Review> findAllByReviewMessageContainingIgnoreCaseOrProduct_NameContainingIgnoreCase(String name, String category, Pageable pageable);

}
