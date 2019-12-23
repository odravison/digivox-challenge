package br.digivox.odravison.apiserver.controller;

import br.digivox.odravison.apiserver.domain.item.ItemType;
import br.digivox.odravison.apiserver.dto.item.ItemTypeDTO;
import br.digivox.odravison.apiserver.service.Item.ItemTypeService;
import br.digivox.odravison.apiserver.shared.controller.BaseDomainController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("item-types")
public class ItemTypeController extends BaseDomainController<ItemType, Long, ItemTypeDTO> {

    @Autowired
    private ItemTypeService itemTypeService;

    @Override
    public ItemTypeService getService() {
        return this.itemTypeService;
    }

}
