package bts.sio.azurimmo.model.mapper;

import bts.sio.azurimmo.model.Role;
import bts.sio.azurimmo.model.User;
import bts.sio.azurimmo.model.dto.UserDTO;

public class UserMapper {

    public static UserDTO toDTO(User u) {
        if (u == null) return null;

        UserDTO dto = new UserDTO();
        dto.setAdresseMail(u.getAdresseMail());
        dto.setMotDePasse(null);

        if (u.getRole() != null) {
            dto.setRole(u.getRole().getId());
            dto.setRoleLibelle(u.getRole().getLibelle());
        }
        return dto;
    }

    public static User toEntity(UserDTO dto) {
        if (dto == null) return null;
        User u = new User();
        u.setAdresseMail(dto.getAdresseMail());
        u.setMotDePasse(dto.getMotDePasse());

        if (dto.getRole() != null) {
            Role role = new Role();
            role.setId(dto.getRole());
            u.setRole(role);
        }
        return u;
    }
}