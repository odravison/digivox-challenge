package br.digivox.odravison.apiserver.dto.requests;

import br.digivox.odravison.apiserver.shared.dto.DomainDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class RentItemsRequest extends DomainDTO {

    private Long customerId;
    private List<Long> itemsIds;
    private Date rentDate;
    private Date returnDate;

}
