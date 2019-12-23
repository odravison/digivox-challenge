package br.digivox.odravison.apiserver.controller;

import br.digivox.odravison.apiserver.domain.customer.Customer;
import br.digivox.odravison.apiserver.domain.rent.Rent;
import br.digivox.odravison.apiserver.repository.CustomerRepository;
import br.digivox.odravison.apiserver.util.AbstractControllerTest;
import net.bytebuddy.utility.RandomString;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CustomerControllerTest extends AbstractControllerTest {

    private final String BASE_ENDPOINT = "/customers%s";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void createCustomerAndReturn200() throws Exception {
        Customer customerRequest = this.createEntityUtil.generateRandomCustomer();

        mockMvc.perform(post(String.format(BASE_ENDPOINT, ""))
                .content(g.toJson(customerRequest))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty());
    }

    @Test
    public void readCustomerAndReturn200() throws Exception {
        Customer customerSaved = this.createEntityUtil.createRandomCustomer();

        mockMvc.perform(get(String.format(BASE_ENDPOINT, "/" + customerSaved.getId())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(customerSaved.getId().intValue())));

    }

    @Test
    public void updateCustomerAndReturn200() throws Exception {
        Customer customerSaved = this.createEntityUtil.createRandomCustomer();

        customerSaved.setFirstName(RandomString.make(10));

        mockMvc.perform(put(String.format(BASE_ENDPOINT, "/" + customerSaved.getId()))
                .content(g.toJson(customerSaved))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(customerSaved.getId().intValue())))
                .andExpect(jsonPath("$.firstName", is(customerSaved.getFirstName())));

    }

    @Test
    public void deleteCustomerAndReturn200() throws Exception {
        Customer customerSaved = this.createEntityUtil.createRandomCustomer();

        mockMvc.perform(delete(String.format(BASE_ENDPOINT, "/" + customerSaved.getId())))
                .andExpect(status().isOk());
    }

}
