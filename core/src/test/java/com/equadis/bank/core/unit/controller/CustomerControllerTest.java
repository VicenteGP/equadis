package com.equadis.bank.core.unit.controller;

import com.equadis.bank.core.configurations.SpringSecurityConfiguration;
import com.equadis.bank.core.controller.customer.CustomerController;
import com.equadis.bank.core.dto.customer.response.CustomerResponse;
import com.equadis.bank.core.model.customer.CustomerType;
import com.equadis.bank.core.service.CustomerService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(CustomerController.class)
@Import(CustomerController.class)
@ContextConfiguration(classes = SpringSecurityConfiguration.class)
public class CustomerControllerTest {

    @Autowired
    WebApplicationContext context;

    MockMvc mockMvc;

    @MockBean
    CustomerService customerService;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(this.context)
                .apply(springSecurity())
                .build();
    }

    @Test
    @WithMockUser
    public void getCustomer() throws Exception {
        final String username = "equadis";
        when(customerService.getCustomer(eq(username))).thenReturn(
                CustomerResponse.builder()
                        .username(username)
                        .type(CustomerType.LEGAL_ENTITY)
                        .build()
        );

        RequestBuilder request = MockMvcRequestBuilders
                .get("/customer/" + username)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().json(
                        "{type:\"" + CustomerType.LEGAL_ENTITY.name() + "\",username:\"" + username + "\"}"))
                .andReturn();
    }
}
