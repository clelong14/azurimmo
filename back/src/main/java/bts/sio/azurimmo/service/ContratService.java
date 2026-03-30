package bts.sio.azurimmo.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import bts.sio.azurimmo.model.Appartement;
import bts.sio.azurimmo.model.Contrat;
import bts.sio.azurimmo.model.dto.ContratDTO;
import bts.sio.azurimmo.model.mapper.ContratMapper;
import bts.sio.azurimmo.repository.AppartementRepository;
import bts.sio.azurimmo.repository.ContratRepository;

@Service
public class ContratService {
	
	private final ContratRepository contratRepository;
	private final AppartementRepository appartementRepository;

	public ContratService(ContratRepository contratRepository, AppartementRepository appartementRepository) {
		this.contratRepository = contratRepository;
		this.appartementRepository = appartementRepository;
	}
	
	public Contrat saveContrat(Contrat contrat) {
		Contrat savedContrat = contratRepository.save(contrat);
        return savedContrat;
    }
	
	public List<Contrat> getContratsParAppartement(long id) {
        return contratRepository.findByAppartement_Id(id);
    }
	
	public Optional<ContratDTO> getContratDTO(Long id) {
        return contratRepository.findById(id)
                .map(ContratMapper::toDTO);
    }
	
	public List<ContratDTO> getContratsDTO() {
		 return contratRepository.findAll()
		 .stream()
		 .map(ContratMapper::toDTO)
		 .collect(Collectors.toList());
		}
	
	public ContratDTO saveContratDTO(ContratDTO dto) {
		Contrat entity = ContratMapper.toEntity(dto);
        if (dto.getAppartement() != null) { 
            Optional<Appartement> appartement = appartementRepository.findById(dto.getAppartement());
            appartement.ifPresent(entity::setAppartement);
        }
        
        Contrat saved = contratRepository.save(entity);
        return ContratMapper.toDTO(saved);
    }

	public Optional<ContratDTO> updateContratDTO(Long id, ContratDTO dto) {
		return contratRepository.findById(id).map(existing -> {
			existing.setDateDebut(dto.getDateDebut());
			existing.setDateFin(dto.getDateFin());
			existing.setMontantBrut(dto.getMontantBrut());
			existing.setMontantCharges(dto.getMontantCharges());

			if (dto.getAppartement() != null) {
				Optional<Appartement> appartement = appartementRepository.findById(dto.getAppartement());
				appartement.ifPresent(existing::setAppartement);
			}

			Contrat saved = contratRepository.save(existing);
			return ContratMapper.toDTO(saved);
		});
	}

}
