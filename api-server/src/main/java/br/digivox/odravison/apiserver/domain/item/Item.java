package br.digivox.odravison.apiserver.domain.item;

import br.digivox.odravison.apiserver.shared.domain.Domain;
import lombok.*;
import org.hibernate.annotations.ColumnTransformer;
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

    public static final String IS_ITEM_AVAILABLE_CLAUSE = "NOT EXISTS(SELECT * FROM rent r WHERE r.rent_date = CURRENT_DATE AND r.deleted = false AND EXISTS(SELECT * FROM rent_items_used riu WHERE riu.items_used_id = id AND riu.rent_id = r.id) and deleted = false)";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "item_seq")
    @SequenceGenerator(name="item_seq", sequenceName = "item_seq")
    @Column(name = "id")
    private Long id;

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

    @NonNull
    @NotNull
    @ManyToOne(optional = false, fetch = FetchType.EAGER, targetEntity = ItemType.class)
    private ItemType type;

    @Column(name = "is_available", insertable = false, updatable = false)
    @ColumnTransformer(read = Item.IS_ITEM_AVAILABLE_CLAUSE)
    private Boolean availableToday;

    /**
     * If it is deleted.
     */
    @Column(name = "deleted")
    private boolean deleted;
}
