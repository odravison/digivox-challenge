package br.digivox.odravison.apiserver.shared.service;

import br.digivox.odravison.apiserver.shared.domain.Domain;
import br.digivox.odravison.apiserver.shared.repository.BaseDomainRepository;

import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;

public abstract class BaseDomainService<M extends Domain<T>, T extends Serializable> {

    protected abstract BaseDomainRepository<M, T> getRepository();

    public M findOne(T id) {
        return getRepository().findById(id).get();
    }

    public List<M> findAll(){
        return getRepository().findAll();
    }

    @Transactional
    public M insert(M domain) {
        return getRepository().save(domain);
    }

    @Transactional
    public void update(T id, M domain) {
        getRepository().save(domain);
    }

    @Transactional
    public void delete(T id) {
        getRepository().deleteById(id);
    }

}
