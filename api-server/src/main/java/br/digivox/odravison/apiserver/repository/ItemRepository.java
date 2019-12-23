package br.digivox.odravison.apiserver.repository;

import br.digivox.odravison.apiserver.domain.item.Item;
import br.digivox.odravison.apiserver.shared.repository.BaseDomainRepository;

import java.util.List;
import java.util.Set;

public interface ItemRepository extends BaseDomainRepository<Item, Long> {

    public Set<Item> findByIdIn(List<Long> ids);

}
