package br.digivox.odravison.apiserver.controller;

import br.digivox.odravison.apiserver.domain.item.Item;
import br.digivox.odravison.apiserver.dto.item.ItemDTO;
import br.digivox.odravison.apiserver.service.Item.ItemService;
import br.digivox.odravison.apiserver.shared.controller.BaseDomainController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("items")
public class ItemController extends BaseDomainController<Item, Long, ItemDTO> {

    @Autowired
    private ItemService itemService;

    @Override
    public ItemService getService() {
        return this.itemService;
    }
}
