package com.equadis.bank.core.controller.accounting;

import com.equadis.bank.core.dto.accounting.AccountCreatedResponse;
import com.equadis.bank.core.dto.accounting.CreateSavingsAccountRequest;
import com.equadis.bank.core.model.customer.Customer;
import com.equadis.bank.core.service.AccountingService;
import com.equadis.bank.core.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/account/savings", produces = "application/json; charset=UTF-8")
public class SavingsAccountController extends AccountController {

    public SavingsAccountController(AccountingService accountingService, CustomerService customerService) {
        super(accountingService, customerService);
    }

    @PostMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public AccountCreatedResponse createCheckingAccount(@RequestBody @Valid CreateSavingsAccountRequest createSavingsAccountRequest) {
        Customer customer = getCustomerService().findByUsername(createSavingsAccountRequest.username());
        return getAccountingService().createSavingsAccount(customer, createSavingsAccountRequest);
    }

    @PatchMapping(path = "{identificationNumber}/enable-transactions/{enable}")
    public void enableTransactions(@PathVariable String identificationNumber, @PathVariable boolean enable) {
        getAccountingService().enableTransactions(identificationNumber, enable);
    }



    //update interest
    //apply interest
    //enable transactions (Admin)
}
