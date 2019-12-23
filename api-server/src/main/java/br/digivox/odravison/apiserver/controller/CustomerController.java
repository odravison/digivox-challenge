package br.digivox.odravison.apiserver.controller;

import br.digivox.odravison.apiserver.domain.customer.Customer;
import br.digivox.odravison.apiserver.dto.customer.CustomerDTO;
import br.digivox.odravison.apiserver.service.customer.CustomerService;
import br.digivox.odravison.apiserver.shared.controller.BaseDomainController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("customers")
public class CustomerController extends BaseDomainController<Customer, Long, CustomerDTO> {

    @Autowired
    private CustomerService customerService;

    @Override
    public CustomerService getService() {
        return this.customerService;
    }


}
