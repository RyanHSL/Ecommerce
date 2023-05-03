package com.mrui22.ecommerce.controller;

import com.mrui22.ecommerce.common.APIResponse;
import com.mrui22.ecommerce.model.Category;
import com.mrui22.ecommerce.repository.CategoryRepository;
import com.mrui22.ecommerce.services.CategoryServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    CategoryServices categoryService;

    @PostMapping("/create")
    public ResponseEntity<APIResponse> createCategory(@RequestBody Category category) {
        categoryService.createCategory(category);
        return new ResponseEntity<>(new APIResponse(true, "Created a new category"), HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<List<Category>> listCategory(){
        List<Category> body = categoryService.listCategory();
        return new ResponseEntity<List<Category>>(body, HttpStatus.OK);
    }

    @PostMapping("/update/{categoryId}")
    public ResponseEntity<APIResponse> updateCategory(@PathVariable("categoryId") int categoryId, @RequestBody Category category) {
        if (!categoryService.findById(categoryId)) {
            return new ResponseEntity<>(new APIResponse(false, "Category does not exist"), HttpStatus.NOT_FOUND);
        }
        categoryService.editCategory(categoryId, category);

        return new ResponseEntity<>(new APIResponse(true, "Category has been updated"), HttpStatus.OK);
    }
}
