package br.digivox.odravison.apiserver.util;

import br.digivox.odravison.apiserver.domain.customer.Customer;
import br.digivox.odravison.apiserver.domain.item.Item;
import br.digivox.odravison.apiserver.domain.item.ItemType;
import br.digivox.odravison.apiserver.domain.rent.Rent;
import br.digivox.odravison.apiserver.dto.rent.RentDTO;
import br.digivox.odravison.apiserver.dto.requests.RentItemsRequest;
import br.digivox.odravison.apiserver.dto.requests.ReserveItemsRequestDTO;
import br.digivox.odravison.apiserver.enums.RentSituation;
import br.digivox.odravison.apiserver.repository.CustomerRepository;
import br.digivox.odravison.apiserver.repository.ItemRepository;
import br.digivox.odravison.apiserver.repository.ItemTypeRepository;
import br.digivox.odravison.apiserver.repository.RentRepository;
import br.digivox.odravison.apiserver.service.rent.RentService;
import br.digivox.odravison.apiserver.shared.exception.BusinessException;
import org.modelmapper.internal.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;

@Component
public class CreateEntityUtil {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private RentRepository rentRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ItemTypeRepository itemTypeRepository;

    @Autowired
    private RentService rentService;

    public Item createItem(String name, BigDecimal value, ItemType type){
        Item item = new Item(name, value, type);
        return this.itemRepository.save(item);
    }

    public Item createRandomItem() {

        Item item = generateRandomItem();

        return this.itemRepository.save(item);
    }

    public Item generateRandomItem() {

        String name = RandomString.make(10);
        BigDecimal value = BigDecimal.TEN;
        ItemType type = this.createRandomItemType();

        return new Item(name, value, type);
    }

    public Customer createCustomer(String firstName, String lastName, String cpf, String email) {
        Customer customer = new Customer(firstName, lastName, cpf, email);
        return customerRepository.save(customer);
    }

    public Customer generateRandomCustomer() {
        String firstName = RandomString.make(10);
        String lastName = RandomString.make(10);
        String cpf = RandomString.make(11).replaceAll("[a-zA-Z]", "0");
        String email = RandomString.make(10);

        return new Customer(firstName, lastName, cpf, email);
    }

    public Customer createRandomCustomer() {

        Customer customer = generateRandomCustomer();

        return this.customerRepository.save(customer);
    }

    public Rent createRent(Date rentDate, Date returnDate, RentSituation situation) {
        Customer customer = this.createRandomCustomer();
        Rent rent = new Rent(customer.getId(), rentDate, returnDate, situation);

        return this.rentRepository.save(rent);
    }

    public Rent createRandomRent() {
        Rent rent = generateRentRandom();

        return this.rentRepository.save(rent);
    }

    public Rent generateRentRandom() {
        Customer customer = this.createRandomCustomer();

        Date rentDate = new Date();

        Calendar c = Calendar.getInstance();
        c.setTime(rentDate);
        c.add(Calendar.DATE, 1);
        Date returnDate = c.getTime();

        Set<Item> items = new HashSet<>();
        items.add(this.createRandomItem());
        items.add(this.createRandomItem());
        items.add(this.createRandomItem());
        items.add(this.createRandomItem());
        items.add(this.createRandomItem());

        Rent rent = new Rent(customer.getId(), rentDate, returnDate, RentSituation.RENTED);

        rent.setItemsUsed(items);

        return rent;

    }

    public ItemType createItemType(String type) {

        ItemType itemType = new ItemType(type);

        return this.itemTypeRepository.save(itemType);
    }

    public ItemType createRandomItemType() {
        ItemType itemType = generateItemTypeRandom();

        return this.itemTypeRepository.save(itemType);
    }

    public ItemType generateItemTypeRandom() {
        ItemType itemType = new ItemType(RandomString.make(10));

        return itemType;

    }

    public RentDTO reserveItems() throws BusinessException {
        Item itemSaved1 = this.createRandomItem();
        Item itemSaved2 = this.createRandomItem();
        Item itemSaved3 = this.createRandomItem();

        Customer customer = this.createRandomCustomer();

        ReserveItemsRequestDTO dto = new ReserveItemsRequestDTO();

        dto.setCustomerId(customer.getId());
        dto.setItemsIds(List.of(itemSaved1.getId(), itemSaved2.getId(), itemSaved3.getId()));
        dto.setRentDate(new Date());

        Calendar c = Calendar.getInstance();
        c.setTime(dto.getRentDate());
        c.add(Calendar.DATE, 2);

        dto.setReturnDate(c.getTime());

        return this.rentService.reserveItems(dto);

    }

    public RentDTO rentItems() throws BusinessException {
        Item itemSaved1 = this.createRandomItem();
        Item itemSaved2 = this.createRandomItem();
        Item itemSaved3 = this.createRandomItem();

        Customer customer = this.createRandomCustomer();

        RentItemsRequest dto = new RentItemsRequest();

        dto.setCustomerId(customer.getId());
        dto.setItemsIds(List.of(itemSaved1.getId(), itemSaved2.getId(), itemSaved3.getId()));
        dto.setRentDate(new Date());

        Calendar c = Calendar.getInstance();
        c.setTime(dto.getRentDate());
        c.add(Calendar.DATE, 2);

        dto.setReturnDate(c.getTime());

        return this.rentService.rentItems(dto);

    }



}
