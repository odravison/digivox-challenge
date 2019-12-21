package br.digivox.odravison.apiserver.domain.item;

import br.digivox.odravison.apiserver.shared.domain.Domain;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity(name = "item")
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@ToString
@SQLDelete(sql = "UPDATE item SET deleted = true WHERE id = ?")
@Where(clause = Domain.WHERE_DELETED_CLAUSE)
@EqualsAndHashCode(callSuper = false)
public class Item extends Domain<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id = null;

    /**
     * Item name
     */
    @NonNull
    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * Item price
     */
    @NonNull
    @NotNull
    @Column(name = "price", nullable = false, scale = 2)
    @DecimalMin(value = "0.01", message = "Item price must must be higher or equal to 0.01")
    private BigDecimal price;

    /**
     * Item quantity used to identify how much of this item could be rented
     */
    @NonNull
    @NotNull
    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    /**
     * If it is deleted.
     */
    @Column(name = "deleted")
    private boolean deleted;
}
