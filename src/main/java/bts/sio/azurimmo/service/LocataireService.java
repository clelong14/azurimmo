package bts.sio.azurimmo.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bts.sio.azurimmo.model.Locataire;
import bts.sio.azurimmo.model.dto.LocataireDTO;
import bts.sio.azurimmo.model.mapper.LocataireMapper;
import bts.sio.azurimmo.repository.ContratRepository;
import bts.sio.azurimmo.repository.LocataireRepository;
import lombok.Data;

@Data
@Service
public class LocataireService {
    
    @Autowired 
    private LocataireRepository locataireRepository;
    
    @Autowired
    private ContratRepository contratRepository;
    
    public Locataire saveLocataire(Locataire locataire) {
        Locataire savedLocataire = locataireRepository.save(locataire);
        return savedLocataire;
    }
    
    public List<Locataire> getLocatairesParContrat(long id) {
        return locataireRepository.findByContrats_Id(id);
    }
    
    public Optional<LocataireDTO> getLocataireDTO(Long id) {
        return locataireRepository.findById(id)
                .map(LocataireMapper::toDTO);
    }
    
    // CORRECTION : Méthode dupliquée - à supprimer ou renommer
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
        Locataire saved = locataireRepository.save(entity);
        return LocataireMapper.toDTO(saved);
    }
}