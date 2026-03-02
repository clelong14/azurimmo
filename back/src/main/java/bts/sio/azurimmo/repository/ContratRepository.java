package bts.sio.azurimmo.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bts.sio.azurimmo.model.Contrat;

@Repository
public interface ContratRepository extends JpaRepository<Contrat, Long> {

    List<Contrat> findByAppartement_Id(long id);

    List<Contrat> findByDateDebut(Date dateDebut);
}
