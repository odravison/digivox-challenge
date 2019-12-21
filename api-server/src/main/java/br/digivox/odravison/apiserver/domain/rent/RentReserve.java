package br.digivox.odravison.apiserver.domain.rent;

import br.digivox.odravison.apiserver.domain.customer.Customer;
import br.digivox.odravison.apiserver.domain.item.ItemReserved;
import br.digivox.odravison.apiserver.shared.domain.Domain;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;


/**
 *
 *
 * This class is very much like as Rent.
 * The reason to create this entity is to maintain the Single responsibility principle
 *
 *
 *
 */
@Entity(name = "rent_reserve")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@SQLDelete(sql = "UPDATE rent_reserve SET deleted = true WHERE id = ?")
@Where(clause = Domain.WHERE_DELETED_CLAUSE)
@EqualsAndHashCode(callSuper = false)
public class RentReserve extends Domain<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "rent_reserved_id", orphanRemoval = true)
    private Set<ItemReserved> itemsReserved = new HashSet<>();

    @NotNull
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch=FetchType.EAGER)
    @JoinColumn(name = "customer_id")
    private Customer customerOwner;

    /**
     * If it is deleted.
     */
    @Column(name = "deleted")
    private boolean deleted;

}
