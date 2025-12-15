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
@Table(name = "intervention")
public class Intervention {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	 @Column(name="type")
	 private String type;

	 @Column(name="datePrevue")
	 private Date datePrevue;
	 
	 @Column(name="dateRealisee")
	 private Date dateRealisee;
	 
	 @Column(name="description")
	 private String description;
	 
	 @ManyToOne
	 @JoinColumn(name = "appartement_id")
	 private Appartement appartement;
}