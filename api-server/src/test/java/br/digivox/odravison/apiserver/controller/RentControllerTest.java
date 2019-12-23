package br.digivox.odravison.apiserver.controller;

import br.digivox.odravison.apiserver.domain.rent.Rent;
import br.digivox.odravison.apiserver.dto.rent.RentDTO;
import br.digivox.odravison.apiserver.shared.util.MapperDomainUtil;
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
public class RentControllerTest extends AbstractControllerTest {

    private final String BASE_ENDPOINT = "/rents%s";

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void createRentAndReturn200() throws Exception {
        Rent requestRent = this.createEntityUtil.generateRentRandom();

        mockMvc.perform(post(String.format(BASE_ENDPOINT, ""))
                .content(g.toJson(requestRent))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty());
    }

    @Test
    public void readRentAndReturn200() throws Exception {
        Rent rentSaved = this.createEntityUtil.createRandomRent();

        mockMvc.perform(get(String.format(BASE_ENDPOINT, "/" + rentSaved.getId())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(rentSaved.getId().intValue())));

    }

    @Test
    public void updateRentAndReturn200() throws Exception {
        Rent rentSaved = this.createEntityUtil.createRandomRent();

        rentSaved.getItemsUsed().removeIf(item -> item.getId().equals(1));

        mockMvc.perform(put(String.format(BASE_ENDPOINT, "/" + rentSaved.getId()))
                .content(g.toJson(rentSaved))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(rentSaved.getId().intValue())))
                .andExpect(jsonPath("$.itemsUsed.length", is(rentSaved.getItemsUsed().size())));

    }

    @Test
    public void deleteRentAndReturn200() throws Exception {
        Rent rentSaved = this.createEntityUtil.createRandomRent();

        mockMvc.perform(delete(String.format(BASE_ENDPOINT, "/" + rentSaved.getId())))
                .andExpect(status().isOk());
    }




}
