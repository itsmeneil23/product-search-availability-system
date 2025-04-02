package com.example.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.dto.ProductSearchResult;
import com.example.model.Market;
import com.example.model.Product;
import com.example.model.Quantity;
import com.example.repository.MarketRepository;
import com.example.repository.ProductRepository;
import com.example.repository.QuantityRepository;

@Controller
public class HomeController {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private MarketRepository marketRepository;
    @Autowired
    private QuantityRepository quantityRepository;

    @GetMapping("/")
    public String home(Model model) {
        // Get all products for the dropdown
        model.addAttribute("products", productRepository.findAll());
        return "index";
    }

    @PostMapping("/search")
    public String search(@RequestParam Long productId, @RequestParam Integer quantity, Model model) {
        // Get all products for the dropdown
        model.addAttribute("products", productRepository.findAll());
        
        // Get the selected product
        Optional<Product> productOpt = productRepository.findById(productId);
        if (productOpt.isPresent()) {
            Product product = productOpt.get();
            model.addAttribute("selectedProduct", product);
            
            // Get all quantities for this product
            List<Quantity> quantities = quantityRepository.findByPid(productId);
            List<ProductSearchResult> results = new ArrayList<>();
            
            // For each quantity, get the market name if quantity is sufficient
            for (Quantity q : quantities) {
                if (q.getQ() >= quantity) {
                    Optional<Market> marketOpt = marketRepository.findById(q.getMid());
                    if (marketOpt.isPresent()) {
                        Market market = marketOpt.get();
                        results.add(new ProductSearchResult(market.getName(), q.getQ()));
                    }
                }
            }
            
            model.addAttribute("searchResults", results);
        }
        
        return "index";
    }

    @GetMapping("/admin")
    public String admin(Model model) {
        model.addAttribute("message", "Welcome to the Admin Panel");
        model.addAttribute("products", productRepository.findAll());
        model.addAttribute("markets", marketRepository.findAll());
        return "admin";
    }

    @PostMapping("/admin/add-product")
    public String addProduct(@RequestParam String name) {
        Product product = new Product();
        product.setName(name);
        productRepository.save(product);

        // Add quantity entries for all existing markets
        List<Market> markets = marketRepository.findAll();
        for (Market market : markets) {
            Quantity quantity = new Quantity();
            quantity.setMid(market.getId());
            quantity.setPid(product.getId());
            quantity.setQ(0);
            quantityRepository.save(quantity);
        }

        return "redirect:/admin";
    }

    @PostMapping("/admin/delete-product")
    public String deleteProduct(@RequestParam Long id) {
        // Delete all quantity entries for this product
        List<Quantity> quantities = quantityRepository.findByPid(id);
        quantityRepository.deleteAll(quantities);
        
        // Delete the product
        productRepository.deleteById(id);
        return "redirect:/admin";
    }

    @PostMapping("/admin/rename-product")
    public String renameProduct(@RequestParam Long id, @RequestParam String newName) {
        Product product = productRepository.findById(id).orElse(null);
        if (product != null) {
            product.setName(newName);
            productRepository.save(product);
        }
        return "redirect:/admin";
    }

    @PostMapping("/admin/add-market")
    public String addMarket(@RequestParam String name, @RequestParam String password) {
        Market market = new Market();
        market.setName(name);
        market.setPassword(password);
        marketRepository.save(market);

        // Add quantity entries for all existing products
        List<Product> products = productRepository.findAll();
        for (Product product : products) {
            Quantity quantity = new Quantity();
            quantity.setMid(market.getId());
            quantity.setPid(product.getId());
            quantity.setQ(0);
            quantityRepository.save(quantity);
        }

        return "redirect:/admin";
    }

    @PostMapping("/admin/delete-market")
    public String deleteMarket(@RequestParam Long id) {
        // Delete all quantity entries for this market
        List<Quantity> quantities = quantityRepository.findByMid(id);
        quantityRepository.deleteAll(quantities);
        
        // Delete the market
        marketRepository.deleteById(id);
        return "redirect:/admin";
    }

    @PostMapping("/admin/rename-market")
    public String renameMarket(@RequestParam Long id, @RequestParam String newName) {
        Market market = marketRepository.findById(id).orElse(null);
        if (market != null) {
            market.setName(newName);
            marketRepository.save(market);
        }
        return "redirect:/admin";
    }
} 