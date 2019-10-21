package com.edu.mum.service;

import com.edu.mum.domain.Product;
import com.edu.mum.domain.Review;
import java.util.List;
import java.util.Optional;

public interface ReviewService {

    Optional<List<Review>> findAllByProduct(Product product);

}
