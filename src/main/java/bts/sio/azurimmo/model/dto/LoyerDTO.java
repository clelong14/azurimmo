package bts.sio.azurimmo.model.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoyerDTO {
	private Date datePaiement;
	private Double montantPaye;
	private Long contrat;

}