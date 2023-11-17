package com.alifetvaci.loanservice.controller;

import com.alifetvaci.loanservice.entity.Loan;
import com.alifetvaci.loanservice.service.LoanService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
@AllArgsConstructor
public class LoanController {


    private final LoanService loanService;

    @GetMapping(path = "/loans/{type}")
    public ResponseEntity<List<Loan>> getLoansByType(@PathVariable("type") String type) {
        return ResponseEntity.ok().body(loanService.getAllLoansByType(type.toUpperCase()));
    }

}
