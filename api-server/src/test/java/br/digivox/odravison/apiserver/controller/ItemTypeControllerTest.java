package br.digivox.odravison.apiserver.controller;

import br.digivox.odravison.apiserver.domain.customer.Customer;
import br.digivox.odravison.apiserver.domain.item.ItemType;
import br.digivox.odravison.apiserver.repository.CustomerRepository;
import br.digivox.odravison.apiserver.repository.ItemTypeRepository;
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
public class ItemTypeControllerTest extends AbstractControllerTest {

    private final String BASE_ENDPOINT = "/item-types%s";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ItemTypeRepository itemTypeRepository;

    @Test
    public void createItemTypeAndReturn200() throws Exception {
        ItemType itemTypeRequest = this.createEntityUtil.generateItemTypeRandom();

        mockMvc.perform(post(String.format(BASE_ENDPOINT, ""))
                .content(g.toJson(itemTypeRequest))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty());
    }

    @Test
    public void readItemTypeAndReturn200() throws Exception {
        ItemType itemTypeSaved = this.createEntityUtil.createRandomItemType();

        mockMvc.perform(get(String.format(BASE_ENDPOINT, "/" + itemTypeSaved.getId())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(itemTypeSaved.getId().intValue())));

    }

    @Test
    public void updateItemTypeAndReturn200() throws Exception {
        ItemType itemTypeSaved = this.createEntityUtil.createRandomItemType();

        itemTypeSaved.setType(RandomString.make(10));

        mockMvc.perform(put(String.format(BASE_ENDPOINT, "/" + itemTypeSaved.getId()))
                .content(g.toJson(itemTypeSaved))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(itemTypeSaved.getId().intValue())))
                .andExpect(jsonPath("$.type", is(itemTypeSaved.getType())));

    }

    @Test
    public void deleteItemTypeAndReturn200() throws Exception {
        ItemType itemTypeSaved = this.createEntityUtil.createRandomItemType();

        mockMvc.perform(delete(String.format(BASE_ENDPOINT, "/" + itemTypeSaved.getId())))
                .andExpect(status().isOk());
    }

}
