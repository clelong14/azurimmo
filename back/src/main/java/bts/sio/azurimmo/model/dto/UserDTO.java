package bts.sio.azurimmo.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    private String adresseMail;
    private String motDePasse;
    private Long role;
    private String roleLibelle;
}
