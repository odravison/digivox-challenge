package br.digivox.odravison.apiserver.dto.customer;

import br.digivox.odravison.apiserver.shared.dto.DomainDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CustomerDTO extends DomainDTO {

    private Long id;

    private String firstName;

    private String lastName;

    private String cpf;

    private String email;

}
