package br.digivox.odravison.apiserver.repository;

import br.digivox.odravison.apiserver.domain.rent.Rent;
import br.digivox.odravison.apiserver.enums.RentSituation;
import br.digivox.odravison.apiserver.shared.repository.BaseDomainRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface RentRepository extends BaseDomainRepository<Rent, Long> {


    @Query(value = "SELECT EXISTS(SELECT * FROM rent AS r WHERE r.rent_date = ?1 " +
            "AND r.deleted = false " +
            "AND EXISTS(SELECT * FROM rent_items_used AS riu " +
            "WHERE riu.items_used_id IN ?2 " +
            "AND riu.rent_id = r.id))", nativeQuery = true)
    public Boolean itemsRentedOrReserved(Date rentDate, List<Long> itemsIds);

    public Rent findByIdAndRentSituation(Long id, RentSituation situation);
}
