package com.equadis.bank.core.dto.customer.response;

import com.equadis.bank.core.model.customer.CustomerType;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = true)
public class LegalEntityResponse extends CustomerResponse {

    String companyName;

    String registrationNumber;

    @Builder(builderMethodName = "legalEntityBuilder")
    public LegalEntityResponse(CustomerType type, String username, String email, String companyName, String registrationNumber) {
        super(type, username, email);
        this.companyName = companyName;
        this.registrationNumber = registrationNumber;
    }

    @Builder(builderMethodName = "customerBuilder")
    public LegalEntityResponse(CustomerResponse customerResponse, String companyName, String registrationNumber) {
        super(customerResponse.getType(), customerResponse.getUsername(), customerResponse.getUsername());
        this.companyName = companyName;
        this.registrationNumber = registrationNumber;
    }
}
