package com.alifetvaci.loanservice.service;

import com.alifetvaci.loanservice.dto.InterestRate;
import com.alifetvaci.loanservice.entity.Loan;
import com.alifetvaci.loanservice.repository.LoanRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class LoanService {

    private final LoanRepository loanRepository;

    private final RestTemplate restTemplate;

    private static final String SERVICE_NAME = "loan-service";

    private static final String RATE_SERVICE_URL = "http://localhost:9000/api/rates/";

    @CircuitBreaker(name = SERVICE_NAME, fallbackMethod = "fallbackMethod")
    public List<Loan> getAllLoansByType(String type) {
        log.info("Circuit breaker is close");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<InterestRate> entity = new HttpEntity<>(null, headers);
        ResponseEntity<InterestRate> response = restTemplate.exchange(
                (RATE_SERVICE_URL + type),
                HttpMethod.GET, entity,
                InterestRate.class
        );
        InterestRate rate = response.getBody();
        List<Loan> loanList = new ArrayList<>();
        if (rate != null) {
            loanList = loanRepository.findByType(type);
            for (Loan loan : loanList) {
                loan.setInterest(loan.getAmount() * (rate.getRateValue() / 100));
            }
        }
        return loanList;
    }

    public List<Loan> fallbackMethod(Exception e) {
        log.error("Circuit breaker is opened and fallback method working");
        return new ArrayList<>();
    }
}