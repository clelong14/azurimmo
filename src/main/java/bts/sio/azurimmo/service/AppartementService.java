package bts.sio.azurimmo.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bts.sio.azurimmo.model.Appartement;
import bts.sio.azurimmo.model.dto.AppartementDTO;
import bts.sio.azurimmo.model.mapper.AppartementMapper;
import bts.sio.azurimmo.repository.AppartementRepository;
import lombok.Data; 

@Data 
@Service
public class AppartementService {
	
	 @Autowired 
	 private AppartementRepository appartementRepository; // Injection de la couche Repository.

	 // Sauvegarde/mise à jour.
	 public Appartement saveAppartement(Appartement appartement) {
		 Appartement savedAppartement = appartementRepository.save(appartement);
	     return savedAppartement;
	 }
	 
	 // Délégation de la recherche par ville au Repository.
	 public List<Appartement> findByVille(String ville) {
		 return appartementRepository.findByBatiment_Ville(ville);
	 }
	 
	 // Délégation de la recherche par ID de bâtiment.
	 public List<Appartement> getAppartementsParBatiment(long id) {
	        return appartementRepository.findByBatiment_Id(id);
	 }
	 
	 // Délégation de la recherche par surface minimum.
	 public List<Appartement> findBySurfaceGreaterThan(Integer surface) {
	        return appartementRepository.findBySurfaceGreaterThan(surface);
	 }
	 
	 public Optional<AppartementDTO> getAppartementDTO(Long id) {
		 return appartementRepository.findById(id)
				 				  .map(AppartementMapper::toDTO);
	}
	 
	 public Optional<AppartementDTO> getAppartementsDTO(Long id) {
		 return appartementRepository.findById(id)
				 				  .map(AppartementMapper::toDTO);
	}
	
	public List<AppartementDTO> getAppartementsDTO() {
		 return appartementRepository.findAll()
								  .stream()
								  .map(AppartementMapper::toDTO)
								  .collect(Collectors.toList());
	}
	
	public AppartementDTO saveAppartementDTO(AppartementDTO dto) {
			Appartement entity = AppartementMapper.toEntity(dto);
			Appartement saved = appartementRepository.save(entity);
			return AppartementMapper.toDTO(saved);
		 }

}