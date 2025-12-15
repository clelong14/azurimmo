package bts.sio.azurimmo.model.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContratDTO {
	private Date dateDebut;
	private Date dateFin;
	private Double montantBrut;
	private Double montantCharges;
	private Long appartement;

}
