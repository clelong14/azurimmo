package bts.sio.azurimmo.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import bts.sio.azurimmo.model.Appartement;
import bts.sio.azurimmo.model.Intervention;
import bts.sio.azurimmo.model.dto.InterventionDTO;
import bts.sio.azurimmo.model.mapper.InterventionMapper;
import bts.sio.azurimmo.repository.AppartementRepository;
import bts.sio.azurimmo.repository.InterventionRepository;

@Service
public class InterventionService {
	
	private final InterventionRepository interventionRepository;
	private final AppartementRepository appartementRepository;

	public InterventionService(InterventionRepository interventionRepository, AppartementRepository appartementRepository) {
		this.interventionRepository = interventionRepository;
		this.appartementRepository = appartementRepository;
	}
	
	public Intervention saveIntervention(Intervention intervention) {
		Intervention savedIntervention = interventionRepository.save(intervention);
        return savedIntervention;
    }
	
	public List<Intervention> getInterventionsParAppartement(long id) {
        return interventionRepository.findByAppartement_Id(id);
    }
	
	public Optional<InterventionDTO> getInterventionDTO(Long id) {
        return interventionRepository.findById(id)
                .map(InterventionMapper::toDTO);
    }
	
	public List<InterventionDTO> getInterventionsDTO() {
		 return interventionRepository.findAll()
		 .stream()
		 .map(InterventionMapper::toDTO)
		 .collect(Collectors.toList());
		}
	
	public InterventionDTO saveInterventionDTO(InterventionDTO dto) {
		Intervention entity = InterventionMapper.toEntity(dto);
        if (dto.getAppartement() != null) { 
            Optional<Appartement> appartement = appartementRepository.findById(dto.getAppartement());
            appartement.ifPresent(entity::setAppartement);
        }
        
        Intervention saved = interventionRepository.save(entity);
        return InterventionMapper.toDTO(saved);
    }
}
