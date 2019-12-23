package br.digivox.odravison.apiserver.service.Item;

import br.digivox.odravison.apiserver.domain.item.ItemType;
import br.digivox.odravison.apiserver.repository.ItemTypeRepository;
import br.digivox.odravison.apiserver.shared.service.BaseDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemTypeService extends BaseDomainService<ItemType, Long> {

    @Autowired
    private ItemTypeRepository itemTypeRepository;

    @Override
    public ItemTypeRepository getRepository() {
        return this.itemTypeRepository;
    }

}
