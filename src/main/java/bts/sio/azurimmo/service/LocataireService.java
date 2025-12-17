package bts.sio.azurimmo.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bts.sio.azurimmo.model.Contrat;
import bts.sio.azurimmo.model.Locataire;
import bts.sio.azurimmo.model.locataire;
import bts.sio.azurimmo.model.dto.LocataireDTO;
import bts.sio.azurimmo.model.dto.locataireDTO;
import bts.sio.azurimmo.model.mapper.LocataireMapper;
import bts.sio.azurimmo.model.mapper.locataireMapper;
import bts.sio.azurimmo.repository.ContratRepository;
import bts.sio.azurimmo.repository.LocataireRepository;
import bts.sio.azurimmo.repository.locataireRepository;
import bts.sio.azurimmo.repository.locataireRepository;
import lombok.Data;

@Data
@Service
public class LocataireService {
	
	@Autowired 
    private LocataireRepository locataireRepository;
	
	@Autowired 
    private LocataireService locataireService;
	
	@Autowired
    private ContratRepository contratRepository;
	
	public Locataire saveLocataire(Locataire locataire) {
		Locataire savedlocataire = locataireRepository.save(locataire);
        return savedlocataire;
    }
	
	public List<Locataire> getLocatairesParContrat(long id) {
		return locataireRepository.findByContrat_Id(id);
    }
	
	public Optional<LocataireDTO> getLocataireDTO(Long id) {
        return locataireRepository.findById(id)
                .map(LocataireMapper::toDTO);
    }
	
	public Optional<LocataireDTO> getLocatairesDTO(Long id) {
        return locataireRepository.findById(id)
                .map(LocataireMapper::toDTO);
    }
	
	public List<LocataireDTO> getLocatairesDTO() {
		 return locataireRepository.findAll()
		 .stream()
		 .map(LocataireMapper::toDTO)
		 .collect(Collectors.toList());
		}
	
	public LocataireDTO saveLocataireDTO(LocataireDTO dto) {
		Locataire entity = LocataireMapper.toEntity(dto);
        if (dto.getContrat() != null) { 
            Optional<Contrat> contrat = contratRepository.findById(dto.getContrat());
            contrat.ifPresent(entity::setContrat);
        }
        
        Locataire saved = locataireRepository.save(entity);
        return LocataireMapper.toDTO(saved);
    }

}
