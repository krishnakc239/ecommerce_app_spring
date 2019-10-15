package com.edu.mum.service.impl;

import com.edu.mum.domain.Category;
import com.edu.mum.repository.CategoryRepository;
import com.edu.mum.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    private CategoryRepository categoryRepository;
    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }
    @Override
    public Page<Category> getAllCategoryPaged(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    @Override
    public void saveCategory(Category category) {
        categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(Long id) {
         categoryRepository.delete(categoryRepository.findById(id).get());
    }

    @Override
    public Optional<Category> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    @Override
    public List<Category> getAllCategory() {
        return  categoryRepository.findAll();
    }


}
