package com.equadis.bank.core.unit.service;

import com.equadis.bank.core.dto.customer.request.CreateLegalEntityRequest;
import com.equadis.bank.core.dto.customer.request.UpdateLegalEntityRequest;
import com.equadis.bank.core.model.audit.Audit;
import com.equadis.bank.core.model.customer.Customer;
import com.equadis.bank.core.model.customer.LegalEntity;
import com.equadis.bank.core.repository.customer.CustomerRepository;
import com.equadis.bank.core.service.CustomerService;
import com.equadis.bank.core.utils.AuditManager;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    private CustomerService customerService;

    @Before
    public void setup(){
        this.customerService = new CustomerService(customerRepository);
    }

    @Test
    public void testUpdateLegalEntity() {
        UpdateLegalEntityRequest updateLegalEntityRequest = new UpdateLegalEntityRequest("equadis", "email@gmail.com", "Equadis SA");

        final String registrationNumber = "503123123";

        ArgumentCaptor<Customer> captor = ArgumentCaptor.forClass(Customer.class);

        when(customerRepository.findLegalEntityByUsername(eq(updateLegalEntityRequest.username()))).thenReturn(LegalEntity.builder()
                .id(1L)
                .audit(AuditManager.created())
                .username("spring")
                .companyName("java")
                .registrationNumber(registrationNumber)
                .build());

        customerService.updateLegalEntity(updateLegalEntityRequest);

        verify(customerRepository, times(1)).save(captor.capture());

        LegalEntity savedEntity = (LegalEntity) captor.getValue();

        assertEquals(updateLegalEntityRequest.username(), savedEntity.getUsername());
        assertEquals(updateLegalEntityRequest.companyName(), savedEntity.getCompanyName());
        assertEquals(updateLegalEntityRequest.email(), savedEntity.getEmail());

    }
}
