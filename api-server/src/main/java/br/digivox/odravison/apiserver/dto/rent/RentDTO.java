package br.digivox.odravison.apiserver.dto.rent;

import br.digivox.odravison.apiserver.dto.customer.CustomerDTO;
import br.digivox.odravison.apiserver.dto.item.ItemDTO;
import br.digivox.odravison.apiserver.enums.RentSituation;
import br.digivox.odravison.apiserver.shared.dto.DomainDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
public class RentDTO extends DomainDTO {

    private Long id;

    private Long customerOwnerId;

    private CustomerDTO customerOwner;

    private Set<ItemDTO> itemsUsed = new HashSet<>();

    private RentSituation rentSituation;

    private Date rentDate;

    private Date returnDate;

}
