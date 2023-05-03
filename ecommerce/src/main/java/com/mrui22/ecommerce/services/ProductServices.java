package com.mrui22.ecommerce.services;

import com.mrui22.ecommerce.dto.ProductDTO;
import com.mrui22.ecommerce.exceptions.ProductNotExistException;
import com.mrui22.ecommerce.model.Category;
import com.mrui22.ecommerce.model.Product;
import com.mrui22.ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServices {
    @Autowired
    ProductRepository productRepository;

    public void createProduct(ProductDTO productDTO, Category category) {
        Product product = new Product();
        product.setCategory(category);
        product.setDescription(productDTO.getDescription());
        product.setImageUrl(productDTO.getImageUrl());
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        productRepository.save(product);
    }

    public ProductDTO convertProductDTO(Product product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setCategoryId(product.getCategory().getId());
        productDTO.setDescription(product.getDescription());
        productDTO.setId(product.getId());
        productDTO.setImageUrl(product.getImageUrl());
        productDTO.setName(product.getName());
        productDTO.setPrice(product.getPrice());

        return productDTO;
    }

    public List<ProductDTO> getAllProducts() {
        List<Product> allProducts = productRepository.findAll();
        List<ProductDTO> productDTOs = new ArrayList<>();

        for (Product p: allProducts) {
            ProductDTO productDTO = convertProductDTO(p);
            productDTOs.add(productDTO);
        }

        return productDTOs;
    }

    public void updateProduct(ProductDTO productDTO, int productId, Category category) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        try {
            Product product = optionalProduct.get();
            product.setId(productDTO.getId());
            product.setName(productDTO.getName());
            product.setImageUrl(productDTO.getImageUrl());
            product.setDescription(productDTO.getDescription());
            product.setPrice(productDTO.getPrice());
            product.setCategory(category);
            productRepository.save(product);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Product getProductById(Integer productId) throws ProductNotExistException {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (!optionalProduct.isPresent())
            throw new ProductNotExistException("Product id is invalid " + productId);
        return optionalProduct.get();
    }
}
