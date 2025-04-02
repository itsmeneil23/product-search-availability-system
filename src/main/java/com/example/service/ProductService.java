package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.Product;
import com.example.repository.ProductRepository;

@Service
public class ProductService {
    
    @Autowired
    private ProductRepository productRepository;
    
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
    
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }
    
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public Product renameProduct(Long id, String newName) {
        Product product = getProductById(id);
        if (product != null) {
            product.setName(newName);
            return productRepository.save(product);
        }
        return null;
    }
} 