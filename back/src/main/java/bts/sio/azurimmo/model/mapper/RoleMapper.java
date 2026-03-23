package bts.sio.azurimmo.model.mapper;

import bts.sio.azurimmo.model.Role;
import bts.sio.azurimmo.model.dto.RoleDTO;

import java.util.stream.Collectors;

public class RoleMapper {

    public static RoleDTO toDTO(Role r) {
        if (r == null) return null;
        RoleDTO dto = new RoleDTO();
        dto.setLibelle(r.getLibelle());
        if (r.getUsers() != null) {
            dto.setUsers(
                    r.getUsers()
                            .stream()
                            .map(UserMapper::toDTO)
                            .collect(Collectors.toList())
            );
        }
        return dto;
    }

    public static Role toEntity(RoleDTO dto) {
        if (dto == null) return null;
        Role r = new Role();
        r.setLibelle(dto.getLibelle());
        return r;
    }
}