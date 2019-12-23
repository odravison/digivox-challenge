package br.digivox.odravison.apiserver.domain.rent;

import br.digivox.odravison.apiserver.domain.customer.Customer;
import br.digivox.odravison.apiserver.domain.item.Item;
import br.digivox.odravison.apiserver.enums.RentSituation;
import br.digivox.odravison.apiserver.shared.domain.Domain;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;
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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rent_seq")
    @SequenceGenerator(name="rent_seq", sequenceName = "rent_seq")
    @Column(name = "id")
    private Long id;

    @NonNull
    @NotNull
    @Column(name = "customer_id", nullable = false)
    private Long customerOwnerId;

    @OneToOne(orphanRemoval = true, fetch=FetchType.EAGER)
    @JoinColumn(name = "customer_id", nullable = false, insertable = false, updatable = false)
    private Customer customerOwner;

    @NotNull
    @NonNull
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    private Set<Item> itemsUsed = new HashSet<>();

    @NonNull
    @NotNull
    @Column(name = "rent_date")
    private Date rentDate;

    @NonNull
    @NotNull
    @Column(name = "return_date")
    private Date returnDate;

    @NotNull
    @NonNull
    @Enumerated(EnumType.STRING)
    @Column(name = "rent_situation")
    private RentSituation rentSituation;

    /**
     * If it is deleted.
     */
    @Column(name = "deleted")
    private boolean deleted;

}
