package bts.sio.azurimmo.repository;

import org.springframework.stereotype.Repository;

import bts.sio.azurimmo.model.Appartement;
import bts.sio.azurimmo.model.Batiment;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface BatimentRepository extends JpaRepository<Appartement, Long> {
	// Query Method : Recherche par l'ID du Batiment (JOIN implicite).
		List<Batiment> findByApartement_Id(long id); 
		
		// Query Method : Recherche par la Ville du Batiment (JOIN implicite).
		List<Batiment> findByBatiment_Ville(String ville);
		
		// Query Method : Recherche des appartements dont la Surface est Supérieure à la valeur.
		List<Batiment> findBySurfaceGreaterThan(Integer surface);
}