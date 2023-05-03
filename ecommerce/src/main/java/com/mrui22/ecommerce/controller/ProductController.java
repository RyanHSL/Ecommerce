package com.mrui22.ecommerce.controller;

import com.mrui22.ecommerce.common.APIResponse;
import com.mrui22.ecommerce.dto.ProductDTO;
import com.mrui22.ecommerce.model.Category;
import com.mrui22.ecommerce.model.Product;
import com.mrui22.ecommerce.repository.CategoryRepository;
import com.mrui22.ecommerce.repository.ProductRepository;
import com.mrui22.ecommerce.services.ProductServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.mrui22.ecommerce.common.APIResponse;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductServices productServices;

    @Autowired
    CategoryRepository categoryRepo;

    @PostMapping("/add")
    public ResponseEntity<APIResponse> createProduct(ProductDTO productDTO) {
        Optional<Category> optionalCategory = categoryRepo.findById(productDTO.getCategoryId());
        if (!optionalCategory.isPresent()) {
            return new ResponseEntity<>(new APIResponse(false, "Category does not exist!"), HttpStatus.BAD_REQUEST);
        }
        productServices.createProduct(productDTO, optionalCategory.get());
        return new ResponseEntity<>(new APIResponse(true, "Product has been added."), HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<List<ProductDTO>> getProducts() {
        List<ProductDTO> products = productServices.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PutMapping("/update/{productId}")
    public ResponseEntity<APIResponse> updateProduct(@PathVariable("productId") Integer productId, @RequestBody ProductDTO productDTO) {
        Optional<Category> optionalCategory = categoryRepo.findById(productDTO.getCategoryId());
        if (!optionalCategory.isPresent()) {
            return new ResponseEntity<>(new APIResponse(false, "Category does not exist!"), HttpStatus.BAD_REQUEST);
        }
        Category category = optionalCategory.get();
        productServices.updateProduct(productDTO, productId, category);
        return new ResponseEntity<>(new APIResponse(true, "Product has been updated."), HttpStatus.OK);
    }
}
