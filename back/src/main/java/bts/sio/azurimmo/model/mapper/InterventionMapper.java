package bts.sio.azurimmo.model.mapper;

import bts.sio.azurimmo.model.Appartement;
import bts.sio.azurimmo.model.Intervention;
import bts.sio.azurimmo.model.dto.InterventionDTO;

public class InterventionMapper {
	public static InterventionDTO toDTO(Intervention i) {
        if (i == null) return null;
        InterventionDTO dto = new InterventionDTO();
        dto.setType(i.getType());
        dto.setDatePrevue(i.getDatePrevue());
        dto.setDateRealisee(i.getDateRealisee());
        dto.setDescription(i.getDescription());
        
        if (i.getAppartement() != null) {
            dto.setAppartement(i.getAppartement().getId());
        }
        return dto;
    }
	 
    public static Intervention toEntity(InterventionDTO dto) {
        if (dto == null) return null;
        Intervention i = new Intervention();
        i.setType(dto.getType());
        i.setDatePrevue(dto.getDatePrevue());
        i.setDateRealisee(dto.getDateRealisee());
        i.setDescription(dto.getDescription());
        
        if (dto.getAppartement() != null) {
            Appartement appartement = new Appartement();
            appartement.setId(dto.getAppartement());
            i.setAppartement(appartement);
        }
        return i;
    }

}
