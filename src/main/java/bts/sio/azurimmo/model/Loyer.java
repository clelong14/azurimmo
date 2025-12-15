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
@Table(name = "loyer")
public class Loyer {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	 @Column(name="datePaiement")
	 private Date datePaiement;
	 
	 @Column(name="montantPaye")
	 private Double montantPaye;
	 
	 @ManyToOne
	 @JoinColumn(name = "contrat_id")
	 private Contrat contrat;
}