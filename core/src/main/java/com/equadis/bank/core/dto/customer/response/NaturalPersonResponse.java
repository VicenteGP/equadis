package com.equadis.bank.core.dto.customer.response;

import com.equadis.bank.core.model.customer.CustomerType;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = true)
public class NaturalPersonResponse extends CustomerResponse {

    String identificationNumber;

    String firstName;

    String lastName;

    @Builder(builderMethodName = "naturalPersonBuilder")
    public NaturalPersonResponse(CustomerType type, String username, String email, String identificationNumber, String firstName, String lastName) {
        super(type, username, email);
        this.identificationNumber = identificationNumber;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Builder(builderMethodName = "customerBuilder")
    public NaturalPersonResponse(CustomerResponse customerResponse, String identificationNumber, String firstName, String lastName) {
        super(customerResponse.getType(), customerResponse.getUsername(), customerResponse.getEmail());
        this.identificationNumber = identificationNumber;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
