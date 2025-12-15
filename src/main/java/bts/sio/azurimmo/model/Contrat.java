package bts.sio.azurimmo.model;

import java.util.Date;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "contrat")
public class Contrat {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	 @Column(name="dateDebut")
	 private Date dateDebut;

	 @Column(name="dateFin")
	 private Date dateFin;
	 
	 @Column(name="montantBrut")
	 private Integer montantBrut;
	 
	 @Column(name="montantCharges")
	 private Integer montantCharges;
	 
	 @ManyToOne
	 @JoinColumn(name = "appartement_id")
	 private Appartement appartement;
}