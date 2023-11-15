package com.equadis.bank.scheduler.service;

import com.equadis.bank.scheduler.model.SavingsAccount;
import com.equadis.bank.scheduler.repository.SavingsAccountRepository;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class TaskScheduler {

    private final RestTemplate restTemplate;

    private final SavingsAccountRepository savingsAccountRepository;

    public TaskScheduler(RestTemplate restTemplate, SavingsAccountRepository savingsAccountRepository) {
        this.restTemplate = restTemplate;
        this.savingsAccountRepository = savingsAccountRepository;
    }

    @Scheduled(cron = "0 1 1 * * *")
    public void applyInterest() {
        LocalDate now = LocalDate.now();
        List<SavingsAccount> savingsAccountNotUpdatedInAYear = savingsAccountRepository.findSavingsAccountNotUpdatedInAYear();
        Set<String> identificationNumbers = savingsAccountNotUpdatedInAYear.stream()
                .map(savingsAccount -> savingsAccount.getIdentificationNumber().toString())
                .collect(Collectors.toSet());

        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("equadis", "dummypassword");
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Object> requestEntity = new HttpEntity<>(identificationNumbers,headers);

        restTemplate.postForObject("http://localhost:8080/transactions/apply-interest", requestEntity, Object.class);

        savingsAccountNotUpdatedInAYear.forEach(savingsAccount -> savingsAccount.setLastUpdate(now));
    }
}
