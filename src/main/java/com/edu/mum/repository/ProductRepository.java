package com.edu.mum.repository;

import com.edu.mum.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
//    Page<Product> findByUserOrderByCreateDateDesc(User user, Pageable pageable);

    Page<Product> findAll(Pageable pageable);

    Optional<Product> findById(Long id);
    List<Product> findAllByCode(String code);
    List<Product> findTop5ByName(Pageable pageable);

//    int countByStatus(boolean status);
//    int countByClaimedStatus(boolean claimed);
//    int countByUserAndStatus(User user, boolean status);
//    int countByUserAndClaimedStatus(User user, boolean claimed);
    Page<Product> findAllByNameContainingIgnoreCaseOrCategory_CategoryNameContainingIgnoreCase(String name, String category, Pageable pageable);

}
