package br.digivox.odravison.apiserver.domain.item;

import br.digivox.odravison.apiserver.shared.domain.Domain;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity(name = "item_rented")
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@ToString
@SQLDelete(sql = "UPDATE item_rented SET deleted = true WHERE id = ?")
@Where(clause = Domain.WHERE_DELETED_CLAUSE)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class ItemRented extends Domain<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @EqualsAndHashCode.Include
    private Long id;

    /**
     * Price of this Item when was rent
     */
    @NonNull
    @NotNull
    @Column(name = "price_rented", updatable = false, nullable = false, scale = 2)
    @DecimalMin(value = "0.01", message = "Item price must must be higher or equal to 0.01")
    private BigDecimal priceRented;

    /**
     * ItemRented quantity to identify how much of this item was rented
     */
    @NonNull
    @NotNull
    @Column(name = "quantity_rented", nullable = false)
    private Integer quantityRented;

    /**
     * Item's id used for this generated ItemRented
     */
    @NonNull
    @NotNull
    @Column(name = "item_id", nullable = false)
    private Long itemSourceId;

    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "item_id", nullable = false, insertable = false, updatable = false)
    private Item itemSource;

    /**
     * Rent's id owner of this ItemRented
     */
    @NonNull
    @NotNull
    @Column(name = "rent_id")
    private Long rentId;

    /**
     * If it is deleted.
     */
    @Column(name = "deleted")
    private boolean deleted;

}
