package com.edu.mum.service;

import com.edu.mum.domain.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    Page<Category> getAllCategoryPaged(Pageable pageable);
    void saveCategory(Category category);
    void deleteCategory(Long id);
    Optional<Category> getCategoryById(Long id);
    List<Category> getAllCategory();
}
