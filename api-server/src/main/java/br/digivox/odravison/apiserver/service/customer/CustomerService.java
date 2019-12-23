package br.digivox.odravison.apiserver.service.customer;

import br.digivox.odravison.apiserver.domain.customer.Customer;
import br.digivox.odravison.apiserver.repository.CustomerRepository;
import br.digivox.odravison.apiserver.shared.repository.BaseDomainRepository;
import br.digivox.odravison.apiserver.shared.service.BaseDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService extends BaseDomainService<Customer, Long> {

    @Autowired
    private CustomerRepository  customerRepository;

    @Override
    protected CustomerRepository getRepository() {
        return this.customerRepository;
    }
}
