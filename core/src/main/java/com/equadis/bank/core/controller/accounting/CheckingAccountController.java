package com.equadis.bank.core.controller.accounting;

import com.equadis.bank.core.dto.accounting.AccountCreatedResponse;
import com.equadis.bank.core.dto.accounting.CreateCheckingAccountRequest;
import com.equadis.bank.core.model.customer.Customer;
import com.equadis.bank.core.service.AccountingService;
import com.equadis.bank.core.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/account/checking", produces = "application/json; charset=UTF-8")
public class CheckingAccountController extends AccountController {

    public CheckingAccountController(AccountingService accountingService, CustomerService customerService) {
        super(accountingService, customerService);
    }

    @PostMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public AccountCreatedResponse createCheckingAccount(@RequestBody @Valid CreateCheckingAccountRequest createCheckingAccountRequest) {
        Customer customer = getCustomerService().findByUsername(createCheckingAccountRequest.username());
        return getAccountingService().createCheckingAccount(customer, createCheckingAccountRequest.initialBalance());
    }
}
