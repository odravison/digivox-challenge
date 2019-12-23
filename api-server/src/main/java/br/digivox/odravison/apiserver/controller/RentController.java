package br.digivox.odravison.apiserver.controller;

import br.digivox.odravison.apiserver.domain.rent.Rent;
import br.digivox.odravison.apiserver.dto.rent.RentDTO;
import br.digivox.odravison.apiserver.dto.requests.RentItemFromReserveRequest;
import br.digivox.odravison.apiserver.dto.requests.RentItemsRequest;
import br.digivox.odravison.apiserver.dto.requests.ReserveItemsRequestDTO;
import br.digivox.odravison.apiserver.dto.requests.ReturnItemsRequest;
import br.digivox.odravison.apiserver.service.rent.RentService;
import br.digivox.odravison.apiserver.shared.controller.BaseDomainController;
import br.digivox.odravison.apiserver.shared.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("rents")
public class RentController extends BaseDomainController<Rent, Long, RentDTO> {

    public final static String POST_RESERVE_ITEMS = "/reserve-items";
    public final static String DELETE_CANCEL_RESERVE = "/reserve-items/{id}/cancel";
    public final static String POST_RENT_ITEMS = "/rent-items";
    public final static String POST_RENT_ITEMS_FROM_RESERVE = "/rent-items-by-reserve";
    public final static String POST_RETURN_ITEMS = "/return-items";

    @Autowired
    private RentService rentService;

    @Override
    public RentService getService() {
        return this.rentService;
    }

    @PostMapping(RentController.POST_RESERVE_ITEMS)
    public ResponseEntity<RentDTO> reserveItems(@Valid @RequestBody ReserveItemsRequestDTO reserveRequest) throws BusinessException {

        RentDTO responseDTO = this.getService().reserveItems(reserveRequest);
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping(RentController.DELETE_CANCEL_RESERVE)
    public ResponseEntity<?> cancelReserve(@PathVariable("id") Long reserveId) {

        this.getService().cancelReserve(reserveId);
        return success();
    }

    @PostMapping(RentController.POST_RENT_ITEMS)
    public ResponseEntity<RentDTO> rentItems(@Valid @RequestBody RentItemsRequest rentRequest) throws BusinessException {

        RentDTO responseDTO = this.getService().rentItems(rentRequest);
        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping(RentController.POST_RENT_ITEMS_FROM_RESERVE)
    public ResponseEntity<RentDTO> rentItemsFromReserve(@Valid @RequestBody RentItemFromReserveRequest rentRequest) throws BusinessException {

        RentDTO responseDTO = this.getService().rentItemsFromReserve(rentRequest);
        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping(RentController.POST_RETURN_ITEMS)
    public ResponseEntity<?> returnItems(@Valid @RequestBody ReturnItemsRequest returnRequest) throws BusinessException {

        this.getService().returnItems(returnRequest);
        return success();
    }







}
