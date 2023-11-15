package com.equadis.bank.core.controller.customer;

import com.equadis.bank.core.dto.customer.response.CustomerResponse;
import com.equadis.bank.core.service.CustomerService;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/customer", produces = "application/json; charset=UTF-8")
@Getter
@Slf4j
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping(path = "/{username}")
    public CustomerResponse getCustomer(@PathVariable String username) {
        log.info("Get Customer '{}'.", username);
        return this.customerService.getCustomer(username);
    }
}
