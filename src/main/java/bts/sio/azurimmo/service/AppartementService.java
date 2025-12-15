package bts.sio.azurimmo.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bts.sio.azurimmo.model.Appartement;
import bts.sio.azurimmo.model.Batiment;
import bts.sio.azurimmo.model.dto.AppartementDTO;
import bts.sio.azurimmo.model.mapper.AppartementMapper;
import bts.sio.azurimmo.repository.AppartementRepository;
import bts.sio.azurimmo.repository.BatimentRepository;  // ← AJOUT
import lombok.Data; 

@Data 
@Service
public class AppartementService {
	
    @Autowired 
    private AppartementRepository appartementRepository;
    
    @Autowired
    private BatimentRepository batimentRepository;

    public Appartement saveAppartement(Appartement appartement) {
        Appartement savedAppartement = appartementRepository.save(appartement);
        return savedAppartement;
    }
	 
    public List<Appartement> findByVille(String ville) {
        return appartementRepository.findByBatiment_Ville(ville);
    }
	 
    public List<Appartement> getAppartementsParBatiment(long id) {
        return appartementRepository.findByBatiment_Id(id);
    }
	 
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
        if (dto.getBatiment() != null) { 
            Optional<Batiment> batiment = batimentRepository.findById(dto.getBatiment());
            batiment.ifPresent(entity::setBatiment);
        }
        
        Appartement saved = appartementRepository.save(entity);
        return AppartementMapper.toDTO(saved);
    }
}