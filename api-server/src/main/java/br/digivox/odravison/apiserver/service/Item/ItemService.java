package br.digivox.odravison.apiserver.service.Item;

import br.digivox.odravison.apiserver.domain.item.Item;
import br.digivox.odravison.apiserver.dto.item.ItemDTO;
import br.digivox.odravison.apiserver.repository.ItemRepository;
import br.digivox.odravison.apiserver.shared.service.BaseDomainService;
import br.digivox.odravison.apiserver.shared.util.MapperDomainUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ItemService extends BaseDomainService<Item, Long> {

    @Autowired
    private ItemRepository itemRepository;

    @Override
    public ItemRepository getRepository() {
        return this.itemRepository;
    }

    public Set<ItemDTO> findByIds(List<Long> itemsIds) {
        Set<Item> items = this.itemRepository.findByIdIn(itemsIds);
        Set<ItemDTO> response = new HashSet<>();
        if (items.size() > 0) {
            return MapperDomainUtil.toSet(items, ItemDTO.class);
        }
        return response;
    }

}
