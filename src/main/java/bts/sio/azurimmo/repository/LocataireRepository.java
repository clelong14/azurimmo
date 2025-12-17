package bts.sio.azurimmo.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import bts.sio.azurimmo.model.Locataire;

public interface LocataireRepository extends JpaRepository<Locataire, Long>  {
	
	List<Locataire> findByContrat_Id(Long id);

    List<Locataire> findByDateNaiss(Date dateNaiss);

}
