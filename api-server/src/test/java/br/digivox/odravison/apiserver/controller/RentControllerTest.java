package br.digivox.odravison.apiserver.controller;

import br.digivox.odravison.apiserver.domain.customer.Customer;
import br.digivox.odravison.apiserver.domain.item.Item;
import br.digivox.odravison.apiserver.domain.rent.Rent;
import br.digivox.odravison.apiserver.dto.rent.RentDTO;
import br.digivox.odravison.apiserver.dto.requests.RentItemsRequest;
import br.digivox.odravison.apiserver.dto.requests.ReserveItemsRequestDTO;
import br.digivox.odravison.apiserver.dto.requests.ReturnItemsRequest;
import br.digivox.odravison.apiserver.repository.RentRepository;
import br.digivox.odravison.apiserver.util.AbstractControllerTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RentControllerTest extends AbstractControllerTest {

    private final String BASE_ENDPOINT = "/rents%s";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RentRepository rentRepository;

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

        rentSaved.getItemsUsed().removeIf(item -> item.getId().equals(3L));

        mockMvc.perform(put(String.format(BASE_ENDPOINT, "/" + rentSaved.getId()))
                .content(g.toJson(rentSaved))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(rentSaved.getId().intValue())))
                .andExpect(jsonPath("$.itemsUsed.length()", is(rentSaved.getItemsUsed().size())));

    }

    @Test
    public void deleteRentAndReturn200() throws Exception {
        Rent rentSaved = this.createEntityUtil.createRandomRent();

        mockMvc.perform(delete(String.format(BASE_ENDPOINT, "/" + rentSaved.getId())))
                .andExpect(status().isOk());
    }

    @Test
    public void reserveItemAndReturn200() throws Exception {
        Item itemSaved1 = this.createEntityUtil.createRandomItem();
        Item itemSaved2 = this.createEntityUtil.createRandomItem();
        Item itemSaved3 = this.createEntityUtil.createRandomItem();

        Customer customer = this.createEntityUtil.createRandomCustomer();

        ReserveItemsRequestDTO dto = new ReserveItemsRequestDTO();

        dto.setCustomerId(customer.getId());
        dto.setItemsIds(List.of(itemSaved1.getId(), itemSaved2.getId(), itemSaved3.getId()));
        dto.setRentDate(new Date());

        Calendar c = Calendar.getInstance();
        c.setTime(dto.getRentDate());
        c.add(Calendar.DATE, 2);

        dto.setReturnDate(c.getTime());

        mockMvc.perform(post(String.format(BASE_ENDPOINT, RentController.POST_RESERVE_ITEMS))
                .content(g.toJson(dto))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty());
    }

    @Test
    public void cancelReserveItemsAndReturn200() throws Exception {

        RentDTO dto = this.createEntityUtil.reserveItems();

        mockMvc.perform(delete(
                String.format(BASE_ENDPOINT,
                        RentController.DELETE_CANCEL_RESERVE.replace("{id}", dto.getId().toString())
                        )
                    )
                )
                .andExpect(status().isOk());
    }

    @Test
    public void rentItemsAndReturn200() throws Exception {
        Item itemSaved1 = this.createEntityUtil.createRandomItem();
        Item itemSaved2 = this.createEntityUtil.createRandomItem();
        Item itemSaved3 = this.createEntityUtil.createRandomItem();

        Customer customer = this.createEntityUtil.createRandomCustomer();

        RentItemsRequest dto = new RentItemsRequest();

        dto.setCustomerId(customer.getId());
        dto.setItemsIds(List.of(itemSaved1.getId(), itemSaved2.getId(), itemSaved3.getId()));
        dto.setRentDate(new Date());

        Calendar c = Calendar.getInstance();
        c.setTime(dto.getRentDate());
        c.add(Calendar.DATE, 2);

        dto.setReturnDate(c.getTime());

        mockMvc.perform(post(String.format(BASE_ENDPOINT, RentController.POST_RENT_ITEMS))
                .content(g.toJson(dto))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty());
    }

    @Test
    public void returnItemsRentedAndReturn200() throws Exception {

        RentDTO dto = this.createEntityUtil.rentItems();

        ReturnItemsRequest returnDTO = new ReturnItemsRequest();
        returnDTO.setRentId(dto.getId());

        mockMvc.perform(post(String.format(BASE_ENDPOINT, RentController.POST_RETURN_ITEMS))
                .content(g.toJson(returnDTO))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
