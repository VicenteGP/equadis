package com.equadis.bank.core.controller.customer;

import com.equadis.bank.core.dto.customer.request.CreateLegalEntityRequest;
import com.equadis.bank.core.dto.customer.request.UpdateLegalEntityRequest;
import com.equadis.bank.core.service.CustomerService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/customer/legal-entity")
@Slf4j
public class LegalEntityController extends CustomerController{

    public LegalEntityController(CustomerService customerService) {
        super(customerService);
    }

    @PostMapping
    public void createLegalEntity(@RequestBody @Valid CreateLegalEntityRequest createLegalEntityRequest) {
        log.info("Creating legal entity '{}'", createLegalEntityRequest.username());
        getCustomerService().createLegalEntity(createLegalEntityRequest);
    }

    @PutMapping
    public void updateLegalEntity(@RequestBody @Valid UpdateLegalEntityRequest updateLegalEntityRequest) {
        log.info("Updating legal entity '{}'", updateLegalEntityRequest.username());
        getCustomerService().updateLegalEntity(updateLegalEntityRequest);
    }

}
