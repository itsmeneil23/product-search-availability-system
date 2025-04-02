package com.example.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.model.Market;

@Repository
public interface MarketRepository extends JpaRepository<Market, Long> {
    Optional<Market> findByName(String name);
} 