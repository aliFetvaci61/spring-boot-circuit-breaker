package com.alifetvaci.rateservice;

import com.alifetvaci.rateservice.entity.Rate;
import com.alifetvaci.rateservice.repository.RateRepository;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@SpringBootApplication
@AllArgsConstructor
public class RateServiceApplication {

	private final RateRepository rateRepository;

	public static void main(String[] args) {
		SpringApplication.run(RateServiceApplication.class, args);
	}

	@PostConstruct
	public void setupData() {
		rateRepository.saveAll(Arrays.asList(
				Rate.builder().id(1).type("PERSONAL").rateValue(10.0).build(),
				Rate.builder().id(2).type("HOUSING").rateValue(8.0).build()
		));
	}

}
