package com.equadis.bank.core.dto.customer.response;

import com.equadis.bank.core.model.customer.CustomerType;
import lombok.Builder;
import lombok.Data;

@Data
public class CustomerResponse {

    private final CustomerType type;

    private final String username;

    private final String email;

    @Builder
    public CustomerResponse(CustomerType type, String username, String email) {
        this.type = type;
        this.username = username;
        this.email = email;
    }
}
