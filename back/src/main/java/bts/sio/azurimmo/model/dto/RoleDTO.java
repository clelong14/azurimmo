package bts.sio.azurimmo.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RoleDTO {
    private Long id;
    private String libelle;
    private List<UserDTO> users;
}
