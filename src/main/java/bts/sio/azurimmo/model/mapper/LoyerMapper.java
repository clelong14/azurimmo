package bts.sio.azurimmo.model.mapper;

import bts.sio.azurimmo.model.Contrat;
import bts.sio.azurimmo.model.Loyer;
import bts.sio.azurimmo.model.dto.LoyerDTO;

public class LoyerMapper {
	public static LoyerDTO toDTO(Loyer l) {
        if (l == null) return null;
        
        LoyerDTO dto = new LoyerDTO();
        dto.setDatePaiement(l.getDatePaiement());
        dto.setMontantPaye(l.getMontantPaye());
        
        if (l.getContrat() != null) {
            dto.setContrat(l.getContrat().getId());
        }
        return dto;
    }
	 
    public static Loyer toEntity(LoyerDTO dto) {
        if (dto == null) return null;
        Loyer l = new Loyer();
        l.setDatePaiement(dto.getDatePaiement());
        l.setMontantPaye(dto.getMontantPaye());
        
        if (dto.getContrat() != null) {
            Contrat contrat = new Contrat();
            contrat.setId(dto.getContrat());
            l.setContrat(contrat);
        }
        return l;
    }

}
