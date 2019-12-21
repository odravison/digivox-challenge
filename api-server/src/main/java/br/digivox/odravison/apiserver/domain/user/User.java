package br.digivox.odravison.apiserver.domain.user;

import br.digivox.odravison.apiserver.shared.domain.Domain;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity(name = "user")
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@ToString
@SQLDelete(sql = "UPDATE user SET deleted = true WHERE id = ?")
@Where(clause = Domain.WHERE_DELETED_CLAUSE)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class User extends Domain<Long> {

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
     * Password.
     */
    @NonNull
    @NotNull
    @Column(name = "password")
    private String password;

    /**
     * Email
     */
    @NonNull
    @NotNull
    @Column(name = "email")
    @EqualsAndHashCode.Include
    private String email;

    /**
     * If it is deleted.
     */
    @Column(name = "deleted")
    private boolean deleted;

}
