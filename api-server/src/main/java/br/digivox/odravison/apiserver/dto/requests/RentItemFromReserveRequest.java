package br.digivox.odravison.apiserver.dto.requests;

import br.digivox.odravison.apiserver.shared.dto.DomainDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RentItemFromReserveRequest extends DomainDTO {

    private Long reserveId;

}
