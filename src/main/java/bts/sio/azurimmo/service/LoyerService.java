package bts.sio.azurimmo.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bts.sio.azurimmo.model.Contrat;
import bts.sio.azurimmo.model.Loyer;
import bts.sio.azurimmo.model.dto.LoyerDTO;
import bts.sio.azurimmo.model.mapper.LoyerMapper;
import bts.sio.azurimmo.repository.ContratRepository;
import bts.sio.azurimmo.repository.LoyerRepository;
import lombok.Data;

@Data
@Service
public class LoyerService {
	
	@Autowired 
    private LoyerRepository loyerRepository;
	
	@Autowired
    private ContratRepository contratRepository;
	
	public Loyer saveLoyer(Loyer loyer) {
		Loyer savedLoyer = loyerRepository.save(loyer);
        return savedLoyer;
    }
	
	public List<Loyer> getLoyersParContrat(long id) {
		return loyerRepository.findByContrat_Id(id);
    }
	
	public Optional<LoyerDTO> getLoyerDTO(Long id) {
        return loyerRepository.findById(id)
                .map(LoyerMapper::toDTO);
    }
	
	public Optional<LoyerDTO> getLoyersDTO(Long id) {
        return loyerRepository.findById(id)
                .map(LoyerMapper::toDTO);
    }
	
	public List<LoyerDTO> getLoyersDTO() {
		 return loyerRepository.findAll()
		 .stream()
		 .map(LoyerMapper::toDTO)
		 .collect(Collectors.toList());
		}
	
	public LoyerDTO saveLoyerDTO(LoyerDTO dto) {
		Loyer entity = LoyerMapper.toEntity(dto);
        if (dto.getContrat() != null) { 
            Optional<Contrat> contrat = contratRepository.findById(dto.getContrat());
            contrat.ifPresent(entity::setContrat);
        }
        
        Loyer saved = loyerRepository.save(entity);
        return LoyerMapper.toDTO(saved);
    }

}
