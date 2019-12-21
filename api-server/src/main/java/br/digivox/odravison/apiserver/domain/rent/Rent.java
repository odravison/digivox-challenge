package br.digivox.odravison.apiserver.domain.rent;

import br.digivox.odravison.apiserver.domain.customer.Customer;
import br.digivox.odravison.apiserver.domain.item.ItemRented;
import br.digivox.odravison.apiserver.shared.domain.Domain;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "rent")
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@ToString
@SQLDelete(sql = "UPDATE rent SET deleted = true WHERE id = ?")
@Where(clause = Domain.WHERE_DELETED_CLAUSE)
@EqualsAndHashCode(callSuper = false)
public class Rent extends Domain<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @OneToOne(orphanRemoval = true, fetch=FetchType.EAGER)
    @Column(name = "customer_id")
    private Customer customerOwner;

    @NotNull
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "rent_id", cascade = CascadeType.ALL, orphanRemoval = true)
    @Min(value = 1, message = "A rent must have one item rented at least")
    private Set<ItemRented> itemsRented = new HashSet<>();

    /**
     * If it is deleted.
     */
    @Column(name = "deleted")
    private boolean deleted;

}
