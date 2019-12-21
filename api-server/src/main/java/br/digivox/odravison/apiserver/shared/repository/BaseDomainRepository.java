package br.digivox.odravison.apiserver.shared.repository;

import br.digivox.odravison.apiserver.shared.domain.Domain;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.io.Serializable;
import java.util.List;

@NoRepositoryBean
public interface BaseDomainRepository<M extends Domain<T>, T extends Serializable> extends PagingAndSortingRepository<M, Serializable> {

    List<M> findAll();

}
