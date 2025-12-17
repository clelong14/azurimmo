package bts.sio.azurimmo.model;

import java.util.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "locataire")
public class Locataire {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	 @Column(name="nom")
	 private String nom;
	 
	 @Column(name="prenom")
	 private String prenom;
	 
	 @Column(name="telephone")
	 private String telephone;
	 
	 @Column(name="adresseMail")
	 private String adresseMail;
	 
	 @Column(name="dateNaissance")
	 private Date dateNaissance;
	 
	 @OneToMany(mappedBy = "locataire")
	 private List<Contrat> contrats;
}
