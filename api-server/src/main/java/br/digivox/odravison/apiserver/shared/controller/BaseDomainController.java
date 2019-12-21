package br.digivox.odravison.apiserver.shared.controller;

import br.odravison.cdt.shared.domain.Domain;
import br.odravison.cdt.shared.dto.DomainDTO;
import br.odravison.cdt.shared.service.BaseDomainService;
import br.odravison.cdt.shared.util.MapperDomainUtil;
import net.jodah.typetools.TypeResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.Serializable;
import java.util.List;

public abstract class BaseDomainController<M extends Domain<T>, T extends Serializable, D extends DomainDTO> {

    /**
     * Index order of the Model class in generics declaration.
     */
    protected static final Integer DOMAIN_INDEX_ORDER = 0;

    /**
     * Index order of the DTO class in generics declaration.
     */
    protected static final Integer DOMAIN_DTO_INDEX_ORDER = 2;

    protected Logger logger = LoggerFactory.getLogger(getClass());

    protected abstract BaseDomainService<M, T> getService();

    @GetMapping("/{id}")
    public D findOne(HttpServletRequest request, @PathVariable T id) {
        return toDTO(findOneDomain(id));
    }

    @GetMapping("/all")
    public List<D> findAll(HttpServletRequest request) {
        logger.info("findAll method invoked");
        return toList(findAllDomain(), getDTOClass());
    }

    @PostMapping
    public ResponseEntity<D> insert(HttpServletRequest request, @Valid @RequestBody D domainDTO) {
        logger.info("insert method invoked ", domainDTO.toString());
        M domain = getService().insert(toDomain(domainDTO));
        return created(toDTO(domain));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(HttpServletRequest request, @PathVariable T id) {
        logger.info("delete method invoked. id: ", id.toString());
        getService().delete(id);
        return success();
    }

    @PutMapping("/{id}")
    public ResponseEntity<D> update(HttpServletRequest request, @Valid @PathVariable T id, @RequestBody D domainDTO) {
        logger.info("update method invoked ", domainDTO.toString());
        getService().update(id, toDomain(domainDTO));
        return ok(domainDTO);
    }


    protected List<M> findAllDomain() {
        return getService().findAll();
    };

    protected M findOneDomain(T id) {
        return getService().findOne(id);
    }

    protected D toDTO(M domain) {
        return mapTo(domain, getDTOClass());
    }

    protected M toDomain(D domainDTO) {
        return mapTo(domainDTO, getDomainClass());
    }

    protected Class<M> getDomainClass() {
        return (Class<M>) getTypeArg()[DOMAIN_INDEX_ORDER];
    }

    protected Class<D> getDTOClass() {
        return (Class<D>) getTypeArg()[DOMAIN_DTO_INDEX_ORDER];
    }

    protected Class<?>[] getTypeArg() {
        return TypeResolver.resolveRawArguments(BaseDomainController.class, getClass());
    }

    protected <E> ResponseEntity<E> responseEntity(E element, HttpStatus status) {
        return new ResponseEntity<>(element, status);
    }

    protected <S, D> D mapTo(S source, Class<D> destClass) {
        return MapperDomainUtil.mapTo(source, destClass);
    }

    protected <D, S> List<D> toList(List<S> items, Class<D> destClass) {
        return MapperDomainUtil.toList(items, destClass);
    }

    protected <E> ResponseEntity<E> created(E element) {
        return new ResponseEntity<>(element, HttpStatus.CREATED);
    }

    protected ResponseEntity<?> success() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    protected <E> ResponseEntity<E> ok(E element) {
        return responseEntity(element, HttpStatus.OK);
    }

    protected ResponseEntity<?> notFound() {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
