package br.digivox.odravison.apiserver.domain.customer;

import br.digivox.odravison.apiserver.shared.domain.Domain;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_seq")
    @SequenceGenerator(name="customer_seq", sequenceName = "customer_seq")
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
    @Column(name = "cpf", unique = true)
    @EqualsAndHashCode.Include
    private String cpf;

    /**
     * Email
     */
    @NonNull
    @NotNull
    @Column(name = "email", unique = true)
    @EqualsAndHashCode.Include
    private String email;

    /**
     * If it is deleted.
     */
    @Column(name = "deleted")
    private boolean deleted;

}
