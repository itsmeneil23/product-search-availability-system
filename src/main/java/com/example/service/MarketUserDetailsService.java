package com.example.service;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.model.Market;
import com.example.repository.MarketRepository;

@Service
public class MarketUserDetailsService implements UserDetailsService {

    @Autowired
    private MarketRepository marketRepository;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        Market market = marketRepository.findByName(name)
            .orElseThrow(() -> new UsernameNotFoundException("Market not found with name: " + name));

        return new User(
            market.getName(),
            market.getPassword(),
            Collections.singletonList(new SimpleGrantedAuthority("ROLE_MARKET"))
        );
    }
} 