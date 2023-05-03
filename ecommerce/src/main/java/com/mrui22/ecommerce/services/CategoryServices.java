package com.mrui22.ecommerce.services;

import com.mrui22.ecommerce.model.Category;
import com.mrui22.ecommerce.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServices {

    @Autowired
    CategoryRepository categoryRepo;

    public void createCategory(Category category) {
        categoryRepo.save(category);
    }

    public List<Category> listCategory() {
        return categoryRepo.findAll();
    }

    public void editCategory(int categoryId, Category updatedCategory) {
        Category category = categoryRepo.getById(categoryId);
        category.setCategoryName(updatedCategory.getCategoryName());
        category.setDescription(updatedCategory.getDescription());
        category.setImgUrl(updatedCategory.getImgUrl());
        categoryRepo.save(category);
    }

    public boolean findById(int categoryId) {
        return categoryRepo.findById(categoryId).isPresent();
    }
}
