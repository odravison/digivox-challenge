package br.digivox.odravison.apiserver.domain.item;

import br.digivox.odravison.apiserver.shared.domain.Domain;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;


/**
 *
 *
 * This class is very much like as ItemRented.
 * The reason to create this entity is to maintain the Single responsibility principle
 *
 *
 *
 */
@Entity(name = "item_reserved")
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@ToString
@SQLDelete(sql = "UPDATE item_reserved SET deleted = true WHERE id = ?")
@Where(clause = Domain.WHERE_DELETED_CLAUSE)
@EqualsAndHashCode(callSuper = false)
public class ItemReserved extends Domain<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Price of this Item when was reserved
     */
    @NonNull
    @NotNull
    @Column(name = "price_reserved", updatable = false, nullable = false, scale = 2)
    @DecimalMin(value = "0.01", message = "Item price must must be higher or equal to 0.01")
    private BigDecimal priceReserved;

    /**
     * ItemReserved quantity to identify how much of this item was rented
     */
    @NonNull
    @NotNull
    @Column(name = "quantity_reserved", nullable = false)
    private Integer quantityReserved;

    /**
     * Item's id used for this generated ItemReserved
     */
    @NonNull
    @NotNull
    @Column(name = "item_id", nullable = false)
    private Long itemSourceId;

    /**
     * A join column to find item source that generates this entity
     */
    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "item_id", nullable = false, insertable = false, updatable = false)
    private Item itemSource;

    /**
     * If it is deleted.
     */
    @Column(name = "deleted")
    private boolean deleted;

}
