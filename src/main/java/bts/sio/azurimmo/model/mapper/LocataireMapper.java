package bts.sio.azurimmo.model.mapper;

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
        return dto;
    }
     
    public static Locataire toEntity(LocataireDTO dto) {
        if (dto == null) return null;
        Locataire lo = new Locataire();
        lo.setNom(dto.getNom());
        lo.setPrenom(dto.getPrenom());
        lo.setTelephone(dto.getTelephone());
        lo.setAdresseMail(dto.getAdresseMail());
        lo.setDateNaissance(dto.getDateNaissance());
        return lo;
    }
}