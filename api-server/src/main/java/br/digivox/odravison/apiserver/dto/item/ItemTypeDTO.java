package br.digivox.odravison.apiserver.dto.item;

import br.digivox.odravison.apiserver.shared.dto.DomainDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemTypeDTO extends DomainDTO {

    private Long id;
    private String type;

}
