package bts.sio.azurimmo.repository; 

import org.springframework.stereotype.Repository;
import bts.sio.azurimmo.model.Appartement;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository 
public interface AppartementRepository extends JpaRepository<Appartement, Long> {
	
	// Query Method : Recherche par l'ID du Batiment (JOIN implicite).
	List<Appartement> findByBatiment_Id(long id); 
	
	// Query Method : Recherche par la Ville du Batiment (JOIN implicite).
	List<Appartement> findByBatiment_Ville(String ville);
	
	// Query Method : Recherche des appartements dont la Surface est Supérieure à la valeur.
	List<Appartement> findBySurfaceGreaterThan(Integer surface);
}