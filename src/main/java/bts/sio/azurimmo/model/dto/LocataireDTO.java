package bts.sio.azurimmo.model.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LocataireDTO {
	private String nom;
	private String prenom;
	private String telephone;
	private String adresseMail;
	private Date dateNaissance;
	private Long contrat;
}
