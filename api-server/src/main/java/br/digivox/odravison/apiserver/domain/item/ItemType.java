package br.digivox.odravison.apiserver.domain.item;

import br.digivox.odravison.apiserver.shared.domain.Domain;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity(name = "item_type")
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@ToString
@SQLDelete(sql = "UPDATE item_type SET deleted = true WHERE id = ?")
@Where(clause = Domain.WHERE_DELETED_CLAUSE)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class ItemType extends Domain<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "item_type_seq")
    @SequenceGenerator(name="item_type_seq", sequenceName = "item_type_seq")
    @Column(name = "id")
    private Long id;

    @NotNull
    @NonNull
    @Column(name = "type", unique = true)
    private String type;

    /**
     * If it is deleted.
     */
    @Column(name = "deleted")
    private boolean deleted;


}
