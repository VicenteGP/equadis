package com.equadis.bank.core.controller.customer;

import com.equadis.bank.core.dto.customer.request.CreateNaturalPersonRequest;
import com.equadis.bank.core.dto.customer.request.UpdateNaturalPersonRequest;
import com.equadis.bank.core.service.CustomerService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/customer/natural-person")
@Slf4j
public class NaturalPersonController extends CustomerController {

    public NaturalPersonController(CustomerService customerService) {
        super(customerService);
    }

    @PostMapping
    @ResponseBody
    public void createNaturalPerson(@RequestBody @Valid CreateNaturalPersonRequest createNaturalPersonRequest) {
        log.info("Creating natural person '{}'", createNaturalPersonRequest.username());
        getCustomerService().createNaturalPerson(createNaturalPersonRequest);
    }

    @PutMapping
    @ResponseBody
    public void updateNaturalPerson(@RequestBody @Valid UpdateNaturalPersonRequest updateNaturalPersonRequest) {
        log.info("Updating natural person '{}'", updateNaturalPersonRequest.username());
        getCustomerService().updateNaturalPerson(updateNaturalPersonRequest);
    }
}
