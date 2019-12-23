package br.digivox.odravison.apiserver.dto.item;

import br.digivox.odravison.apiserver.shared.dto.DomainDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@Getter
@Setter
public class ItemDTO extends DomainDTO {

    private Long id;

    private String name;

    private BigDecimal price;

    private Integer quantity;

    private ItemTypeDTO type;

}
