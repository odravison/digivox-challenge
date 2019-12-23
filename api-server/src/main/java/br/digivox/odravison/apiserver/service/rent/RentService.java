package br.digivox.odravison.apiserver.service.rent;

import br.digivox.odravison.apiserver.domain.item.Item;
import br.digivox.odravison.apiserver.domain.rent.Rent;
import br.digivox.odravison.apiserver.dto.item.ItemDTO;
import br.digivox.odravison.apiserver.dto.rent.RentDTO;
import br.digivox.odravison.apiserver.dto.requests.RentItemFromReserveRequest;
import br.digivox.odravison.apiserver.dto.requests.RentItemsRequest;
import br.digivox.odravison.apiserver.dto.requests.ReserveItemsRequestDTO;
import br.digivox.odravison.apiserver.dto.requests.ReturnItemsRequest;
import br.digivox.odravison.apiserver.enums.RentSituation;
import br.digivox.odravison.apiserver.repository.RentRepository;
import br.digivox.odravison.apiserver.service.Item.ItemService;
import br.digivox.odravison.apiserver.shared.exception.BusinessException;
import br.digivox.odravison.apiserver.shared.service.BaseDomainService;
import br.digivox.odravison.apiserver.shared.util.MapperDomainUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class RentService extends BaseDomainService<Rent, Long> {

    @Autowired
    private RentRepository rentRepository;

    @Autowired
    private ItemService itemService;

    @Override
    public RentRepository getRepository() {
        return rentRepository;
    }

    public RentDTO reserveItems(ReserveItemsRequestDTO reserveRequest) throws BusinessException {
        this.validateItemsToReserve(reserveRequest);
        return this.locateItems(reserveRequest.getItemsIds(),
                reserveRequest.getRentDate(),
                reserveRequest.getReturnDate(),
                reserveRequest.getCustomerId(),
                RentSituation.RESERVED);
    }

    private RentDTO locateItems(List<Long> itemsIds, Date rentDate, Date returnDate, Long customerId, RentSituation situation) {
        Rent location = new Rent(customerId, rentDate, returnDate, situation);

        Set<ItemDTO> items = this.itemService.findByIds(itemsIds);
        location.setItemsUsed(MapperDomainUtil.toSet(items, Item.class));
        location = this.rentRepository.save(location);

        return MapperDomainUtil.mapTo(location, RentDTO.class);
    }

    public void cancelReserve(Long id){
        Rent reserve = this.rentRepository.findByIdAndRentSituation(id, RentSituation.RESERVED);
        this.rentRepository.delete(reserve);
    }

    public Rent findRentByIdAndSituation(Long id, RentSituation situation) {
        return this.rentRepository.findByIdAndRentSituation(id, situation);
    }

    private void validateItemsToReserve(ReserveItemsRequestDTO reserveRequest) throws BusinessException {
        this.validateItems(reserveRequest.getRentDate(), reserveRequest.getReturnDate(), reserveRequest.getItemsIds());
    }

    private void validateItemsToRent(RentItemsRequest rentRequest) throws BusinessException {
        this.validateItems(rentRequest.getRentDate(), rentRequest.getReturnDate(), rentRequest.getItemsIds());
    }

    private void validateItems(Date rentDate, Date returnDate, List<Long> itemsIds) throws BusinessException {
        Date today = new Date();
        if (returnDate.before(today)) {
            throw new BusinessException("Return date must be after today's date");
        } else if (rentDate.after(returnDate)) {
            throw new BusinessException("Return date must be after rent's date");
        }

        Boolean itemsInUseOrReserved = this.rentRepository.itemsRentedOrReserved(rentDate,
                itemsIds);

        if (itemsInUseOrReserved) {
            throw new BusinessException("Item(s) chosen is(are) not available(s)");
        }
    }


    public RentDTO rentItems(RentItemsRequest rentRequest) throws BusinessException {
        this.validateItemsToRent(rentRequest);
        return this.locateItems(rentRequest.getItemsIds(),
                rentRequest.getRentDate(),
                rentRequest.getReturnDate(),
                rentRequest.getCustomerId(),
                RentSituation.RENTED);
    }

    public RentDTO rentItemsFromReserve(RentItemFromReserveRequest rentRequest) throws BusinessException {
        Rent reserve = this.rentRepository.findByIdAndRentSituation(rentRequest.getReserveId(), RentSituation.RESERVED);
        if (reserve == null) throw new BusinessException("There is no reserve with this id");

        reserve.setRentSituation(RentSituation.RENTED);
        reserve = this.rentRepository.save(reserve);

        return MapperDomainUtil.mapTo(reserve, RentDTO.class);
    }

    public void returnItems(ReturnItemsRequest returnRequest) throws BusinessException {
        Rent rent = this.rentRepository.findByIdAndRentSituation(returnRequest.getRentId(), RentSituation.RENTED);
        if (rent == null) throw new BusinessException("There is no rent with this id");

        this.rentRepository.delete(rent);

    }
}
