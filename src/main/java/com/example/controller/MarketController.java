package com.example.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.dto.MarketProductQuantity;
import com.example.model.Market;
import com.example.model.Product;
import com.example.model.Quantity;
import com.example.repository.MarketRepository;
import com.example.repository.ProductRepository;
import com.example.repository.QuantityRepository;

@Controller
public class MarketController {

    @Autowired
    private MarketRepository marketRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private QuantityRepository quantityRepository;

    @GetMapping("/market-login")
    public String marketLogin() {
        return "market-login";
    }

    @GetMapping("/market")
    public String marketDashboard(Model model) {
        // Get the currently logged-in market's name
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String marketName = auth.getName();
        
        // Find the market by name
        Optional<Market> marketOpt = marketRepository.findByName(marketName);
        if (marketOpt.isPresent()) {
            Market market = marketOpt.get();
            model.addAttribute("marketName", market.getName());
            
            // Get all quantities for this market
            List<Quantity> quantities = quantityRepository.findByMid(market.getId());
            List<MarketProductQuantity> productQuantities = new ArrayList<>();
            
            // For each quantity, get the product name
            for (Quantity q : quantities) {
                Optional<Product> productOpt = productRepository.findById(q.getPid());
                if (productOpt.isPresent()) {
                    Product product = productOpt.get();
                    productQuantities.add(new MarketProductQuantity(
                        product.getId(),
                        product.getName(),
                        q.getQ()
                    ));
                }
            }
            
            model.addAttribute("productQuantities", productQuantities);
        }
        
        return "market";
    }

    @PostMapping("/market/update-quantity")
    public String updateQuantity(@RequestParam Long pid, @RequestParam Integer quantity) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String marketName = auth.getName();
        
        Optional<Market> marketOpt = marketRepository.findByName(marketName);
        if (marketOpt.isPresent()) {
            Market market = marketOpt.get();
            Quantity q = quantityRepository.findByMidAndPid(market.getId(), pid);
            if (q != null) {
                q.setQ(quantity);
                quantityRepository.save(q);
            }
        }
        
        return "redirect:/market";
    }
} 