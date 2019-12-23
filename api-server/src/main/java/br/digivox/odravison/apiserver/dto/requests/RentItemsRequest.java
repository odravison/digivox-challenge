package br.digivox.odravison.apiserver.dto.requests;

import br.digivox.odravison.apiserver.shared.dto.DomainDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class RentItemsRequest extends DomainDTO {

    private Long customerId;
    private List<Long> itemsIds;

    @JsonFormat
            (shape = JsonFormat.Shape.STRING, pattern = "MMM d, yyyy, hh:mm:ss a")
    private Date rentDate;

    @JsonFormat
            (shape = JsonFormat.Shape.STRING, pattern = "MMM d, yyyy, hh:mm:ss a")
    private Date returnDate;

}
