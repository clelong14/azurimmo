package bts.sio.azurimmo.model.mapper;

import bts.sio.azurimmo.model.Appartement;
import bts.sio.azurimmo.model.Batiment;
import bts.sio.azurimmo.model.dto.AppartementDTO;

public class AppartementMapper {
	
    public static AppartementDTO toDTO(Appartement a) {
        if (a == null) return null;
        AppartementDTO dto = new AppartementDTO();
        dto.setNumero(a.getNumero());
        dto.setDescription(a.getDescription());
        dto.setSurface(a.getSurface());
        dto.setNbPieces(a.getNbPieces());
        
        if (a.getBatiment() != null) {
            dto.setBatiment(a.getBatiment().getId());
        }
        return dto;
    }
	 
    public static Appartement toEntity(AppartementDTO dto) {
        if (dto == null) return null;
        Appartement a = new Appartement();
        a.setNumero(dto.getNumero());
        a.setDescription(dto.getDescription());
        a.setSurface(dto.getSurface());
        a.setNbPieces(dto.getNbPieces());
        
        if (dto.getBatiment() != null) {
            Batiment batiment = new Batiment();
            batiment.setId(dto.getBatiment());
            a.setBatiment(batiment);
        }
        return a;
    }
}