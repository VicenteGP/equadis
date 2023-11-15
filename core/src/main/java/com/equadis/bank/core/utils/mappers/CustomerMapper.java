package com.equadis.bank.core.utils.mappers;

import com.equadis.bank.core.dto.customer.response.CustomerResponse;
import com.equadis.bank.core.dto.customer.response.LegalEntityResponse;
import com.equadis.bank.core.dto.customer.response.NaturalPersonResponse;
import com.equadis.bank.core.model.customer.Customer;
import com.equadis.bank.core.model.customer.LegalEntity;
import com.equadis.bank.core.model.customer.NaturalPerson;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CustomerMapper {

    public static CustomerResponse toCustomerResponse(Customer customer) {
        return CustomerResponse.builder()
                .type(customer.getType())
                .email(customer.getEmail())
                .username(customer.getUsername())
                .build();
    }

    public static LegalEntityResponse toLegalEntityResponse(LegalEntity legalEntity) {
        CustomerResponse customerBase = CustomerMapper.toCustomerResponse(legalEntity);
        return LegalEntityResponse.customerBuilder()
                .customerResponse(customerBase)
                .companyName(legalEntity.getCompanyName())
                .registrationNumber(legalEntity.getRegistrationNumber())
                .build();
    }

    public static NaturalPersonResponse toNaturalPersonResponse(NaturalPerson naturalPerson) {
        CustomerResponse customerBase = CustomerMapper.toCustomerResponse(naturalPerson);
        return NaturalPersonResponse.customerBuilder()
                .customerResponse(customerBase)
                .firstName(naturalPerson.getFirstName())
                .lastName(naturalPerson.getLastName())
                .identificationNumber(naturalPerson.getIdentificationNumber())
                .build();
    }
}
