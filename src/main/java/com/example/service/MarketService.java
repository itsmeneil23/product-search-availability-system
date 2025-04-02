package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.Market;
import com.example.repository.MarketRepository;

@Service
public class MarketService {
    
    @Autowired
    private MarketRepository marketRepository;
    
    public List<Market> getAllMarkets() {
        return marketRepository.findAll();
    }
    
    public Market saveMarket(Market market) {
        return marketRepository.save(market);
    }
    
    public Market getMarketById(Long id) {
        return marketRepository.findById(id).orElse(null);
    }

    public void deleteMarket(Long id) {
        marketRepository.deleteById(id);
    }

    public Market renameMarket(Long id, String newName) {
        Market market = getMarketById(id);
        if (market != null) {
            market.setName(newName);
            return marketRepository.save(market);
        }
        return null;
    }
} 