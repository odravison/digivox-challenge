package br.digivox.odravison.apiserver.controller;

import br.digivox.odravison.apiserver.domain.item.Item;
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
public class ItemControllerTest extends AbstractControllerTest {

    private final String BASE_ENDPOINT = "/items%s";

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void createItemAndReturn200() throws Exception {
        Item requestItem = createEntityUtil.generateRandomItem();

        mockMvc.perform(post(String.format(BASE_ENDPOINT, ""))
                .content(g.toJson(requestItem))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty());
    }

    @Test
    public void readItemAndReturn200() throws Exception {
        Item itemSaved = this.createEntityUtil.createRandomItem();

        mockMvc.perform(get(String.format(BASE_ENDPOINT, "/" + itemSaved.getId())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(itemSaved.getId().intValue())));

    }

    @Test
    public void updateItemAndReturn200() throws Exception {
        Item itemSaved = this.createEntityUtil.createRandomItem();
        String newName = RandomString.make(10);

        itemSaved.setName(newName);

        mockMvc.perform(put(String.format(BASE_ENDPOINT, "/" + itemSaved.getId()))
                .content(g.toJson(itemSaved))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(itemSaved.getId().intValue())))
                .andExpect(jsonPath("$.name", is(newName)));

    }

    @Test
    public void deleteItemAndReturn200() throws Exception {
        Item itemSaved = this.createEntityUtil.createRandomItem();

        mockMvc.perform(delete(String.format(BASE_ENDPOINT, "/" + itemSaved.getId())))
                .andExpect(status().isOk());
    }
}
