package com.equadis.bank.core.service;

import com.equadis.bank.core.dto.customer.request.CreateLegalEntityRequest;
import com.equadis.bank.core.dto.customer.request.CreateNaturalPersonRequest;
import com.equadis.bank.core.dto.customer.request.UpdateLegalEntityRequest;
import com.equadis.bank.core.dto.customer.request.UpdateNaturalPersonRequest;
import com.equadis.bank.core.dto.customer.response.CustomerResponse;
import com.equadis.bank.core.exceptions.NotFoundException;
import com.equadis.bank.core.exceptions.errors.CustomerErrorCode;
import com.equadis.bank.core.model.customer.Customer;
import com.equadis.bank.core.model.customer.LegalEntity;
import com.equadis.bank.core.model.customer.NaturalPerson;
import com.equadis.bank.core.repository.customer.CustomerRepository;
import com.equadis.bank.core.utils.AuditManager;
import com.equadis.bank.core.utils.mappers.CustomerMapper;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    /**
     * Get Customer Information given username
     *
     * @param username Customer unique username
     * @return CustomerResponse
     */
    public CustomerResponse getCustomer(String username) {
        Customer customer = findByUsername(username);
        return CustomerMapper.toCustomerResponse(customer);
    }

    /**
     * Create a new legal entity.
     *
     * @param createLegalEntityRequest request to create
     */
    public void createLegalEntity(CreateLegalEntityRequest createLegalEntityRequest) {
        LegalEntity legalEntity = LegalEntity.builder()
                .audit(AuditManager.created())
                .username(createLegalEntityRequest.username())
                .email(createLegalEntityRequest.email())
                .companyName(createLegalEntityRequest.companyName())
                .registrationNumber(createLegalEntityRequest.registrationNumber())
                .build();

        customerRepository.save(legalEntity);
    }

    /**
     * Create a new natural person.
     *
     * @param createNaturalPersonRequest request to create
     */
    public void createNaturalPerson(CreateNaturalPersonRequest createNaturalPersonRequest) {
        NaturalPerson naturalPerson = NaturalPerson.builder()
                .audit(AuditManager.created())
                .username(createNaturalPersonRequest.username())
                .email(createNaturalPersonRequest.email())
                .firstName(createNaturalPersonRequest.firstName())
                .lastName(createNaturalPersonRequest.lastName())
                .identificationNumber(createNaturalPersonRequest.identificationNumber())
                .build();

        customerRepository.save(naturalPerson);
    }



    /**
     * Update an existent legal entity.
     *
     * @param updateLegalEntityRequest data to be updated
     */
    public void updateLegalEntity(UpdateLegalEntityRequest updateLegalEntityRequest) {
        LegalEntity legalEntity = (LegalEntity) customerRepository.findLegalEntityByUsername(updateLegalEntityRequest.username());

        if (Objects.nonNull(updateLegalEntityRequest.username())) {
            legalEntity.setUsername(updateLegalEntityRequest.username());
        }

        if (Objects.nonNull(updateLegalEntityRequest.companyName())) {
            legalEntity.setCompanyName(updateLegalEntityRequest.companyName());
        }

        if (Objects.nonNull(updateLegalEntityRequest.email())) {
            legalEntity.setEmail(updateLegalEntityRequest.email());
        }

        legalEntity.setAudit(AuditManager.updated(legalEntity.getAudit()));

        customerRepository.save(legalEntity);
    }


    /**
     * Update an existent natural person.
     *
     * @param updateNaturalPersonRequest data to be updated
     */
    public void updateNaturalPerson(UpdateNaturalPersonRequest updateNaturalPersonRequest) {
        NaturalPerson naturalPerson = (NaturalPerson) customerRepository.findNaturalPersonByUsername(updateNaturalPersonRequest.username());

        if (Objects.nonNull(updateNaturalPersonRequest.username())) {
            naturalPerson.setUsername(updateNaturalPersonRequest.username());
        }

        if (Objects.nonNull(updateNaturalPersonRequest.email())) {
            naturalPerson.setEmail(updateNaturalPersonRequest.username());
        }

        if (Objects.nonNull(updateNaturalPersonRequest.email())) {
            naturalPerson.setFirstName(updateNaturalPersonRequest.firstName());
        }

        if (Objects.nonNull(updateNaturalPersonRequest.lastName())) {
            naturalPerson.setLastName(updateNaturalPersonRequest.lastName());
        }

        naturalPerson.setAudit(AuditManager.updated(naturalPerson.getAudit()));

        customerRepository.save(naturalPerson);
    }


    /**
     * Find Customer given username.
     *
     * @param username Customer unique username
     * @return Customer
     */
    public Customer findByUsername(String username) {
        return Optional.ofNullable(customerRepository.findByUsername(username))
                .orElseThrow(() -> new NotFoundException(CustomerErrorCode.CUSTOMER_NOT_FOUND, username));
    }

}
