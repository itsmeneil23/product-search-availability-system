package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.model.Quantity;

@Repository
public interface QuantityRepository extends JpaRepository<Quantity, Long> {
    List<Quantity> findByMid(Long mid);
    List<Quantity> findByPid(Long pid);
    Quantity findByMidAndPid(Long mid, Long pid);
} 