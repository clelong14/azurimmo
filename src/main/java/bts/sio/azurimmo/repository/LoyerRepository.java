package bts.sio.azurimmo.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import bts.sio.azurimmo.model.Loyer;

public interface LoyerRepository extends JpaRepository<Loyer, Long>  {
	
	List<Loyer> findByContrat_Id(Long id);

    List<Loyer> findByDatePaiement(Date datePaiement);

}
