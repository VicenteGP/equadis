package com.equadis.bank.core.controller.accounting;

import com.equadis.bank.core.dto.accounting.AccountBalanceResponse;
import com.equadis.bank.core.model.customer.Customer;
import com.equadis.bank.core.service.AccountingService;
import com.equadis.bank.core.service.CustomerService;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Getter
@RestController
@RequestMapping(path = "/account", produces = "application/json; charset=UTF-8")
@Slf4j
public class AccountController {

    private final AccountingService accountingService;

    private final CustomerService customerService;

    public AccountController(AccountingService accountingService, CustomerService customerService) {
        this.accountingService = accountingService;
        this.customerService = customerService;
    }

    @GetMapping(path = "/{identificationNumber}/balance")
    public AccountBalanceResponse getAccountBalance(@PathVariable String identificationNumber) {
        log.info("Get account number '{}' balance.", identificationNumber);
        return accountingService.getAccountBalance(identificationNumber);
    }

    @PostMapping(path = "/{identificationNumber}/add-customer/{username}")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void addCustomer(@PathVariable String identificationNumber, @PathVariable String username) {
        log.info("Add Customer '{}' to account number '{}'.", username, identificationNumber);
        Customer customer = customerService.findByUsername(username);
        accountingService.addCustomer(identificationNumber, customer);
    }
}