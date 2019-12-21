package br.digivox.odravison.apiserver.domain.customer;

import br.digivox.odravison.apiserver.domain.rent.Rent;
import br.digivox.odravison.apiserver.domain.rent.RentReserve;
import br.digivox.odravison.apiserver.shared.domain.Domain;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "customer")
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@ToString
@SQLDelete(sql = "UPDATE customer SET deleted = true WHERE id = ?")
@Where(clause = Domain.WHERE_DELETED_CLAUSE)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class Customer extends Domain<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * First name.
     */
    @NonNull
    @NotNull
    @Column(name = "first_name")
    private String firstName;

    /**
     * Last name.
     */
    @NonNull
    @NotNull
    @Column(name = "last_name")
    private String lastName;

    /**
     * CPF
     */
    @NonNull
    @NotNull
    @Column(name = "cpf")
    @EqualsAndHashCode.Include
    private String cpf;

    /**
     * Email
     */
    @NonNull
    @NotNull
    @Column(name = "email")
    @EqualsAndHashCode.Include
    private String email;

    /**
     * Rents made for this user
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer_id")
    private Set<Rent> rents = new HashSet<>();

    /**
     * Reserves made for this user
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer_id")
    private Set<RentReserve> rentReserves = new HashSet<>();

    /**
     * If it is deleted.
     */
    @Column(name = "deleted")
    private boolean deleted;

}
