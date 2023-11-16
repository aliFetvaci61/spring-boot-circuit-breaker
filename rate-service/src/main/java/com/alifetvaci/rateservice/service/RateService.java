package com.alifetvaci.rateservice.service;

import com.alifetvaci.rateservice.entity.Rate;
import com.alifetvaci.rateservice.repository.RateRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RateService {

    private final RateRepository repository;

    public Rate getRateByType(String type) {
        return repository.findByType(type).orElseThrow(() -> new RuntimeException("Rate Not Found: " + type));
    }

}
