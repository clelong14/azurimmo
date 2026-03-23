package bts.sio.azurimmo.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RoleDTO {
    private String libelle;
    private List<UserDTO> users;
}
