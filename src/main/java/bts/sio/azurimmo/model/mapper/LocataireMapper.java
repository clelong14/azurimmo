package bts.sio.azurimmo.model.mapper;

import bts.sio.azurimmo.model.Contrat;
import bts.sio.azurimmo.model.Locataire;
import bts.sio.azurimmo.model.dto.LocataireDTO;

public class LocataireMapper {
	public static LocataireDTO toDTO(Locataire lo) {
        if (lo == null) return null;
        
        LocataireDTO dto = new LocataireDTO();
        dto.setNom(lo.getNom());
        dto.setPrenom(lo.getPrenom());
        dto.setTelephone(lo.getTelephone());
        dto.setAdresseMail(lo.getAdresseMail());
        dto.setDateNaissance(lo.getDateNaissance());
        
        if (lo.getContrats() != null) {
            dto.setContrat(lo.getContrats().getId());
        }
        return dto;
	}
	 
    public static Locataire toEntity(LocataireDTO dto) {
        if (dto == null) return null;
        Locataire lo = new Locataire();
        lo.setNom(lo.getNom());
        lo.setPrenom(lo.getPrenom());
        lo.setTelephone(lo.getTelephone());
        lo.setAdresseMail(lo.getAdresseMail());
        lo.setDateNaissance(lo.getDateNaissance());
        
        if (dto.getContrat() != null) {
            Contrat contrat = new Contrat();
            contrat.setId(dto.getContrat());
            lo.setContrat(contrat);
        }
        return lo;
    }
}
