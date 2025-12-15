package bts.sio.azurimmo.model.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InterventionDTO {
	private String type;
	private Date datePrevue;
	private Date dateRealisee;
	private String description;
	private Long appartement;

}
