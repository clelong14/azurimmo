package bts.sio.azurimmo.service;

import bts.sio.azurimmo.model.Role;
import bts.sio.azurimmo.model.dto.RoleDTO;
import bts.sio.azurimmo.model.mapper.RoleMapper;
import bts.sio.azurimmo.repository.RoleRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    // Récupérer tous les rôles
    public List<RoleDTO> getAllRolesDTO() {
        return roleRepository.findAll()
                .stream()
                .map(RoleMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Récupérer un rôle par ID
    public Optional<RoleDTO> getRoleDTO(Long id) {
        return roleRepository.findById(id)
                .map(RoleMapper::toDTO);
    }

    // Créer un rôle
    public RoleDTO saveRoleDTO(RoleDTO dto) {
        Role entity = RoleMapper.toEntity(dto);
        Role saved = roleRepository.save(entity);
        return RoleMapper.toDTO(saved);
    }

    // Supprimer un rôle
    public void deleteRole(Long id) {
        roleRepository.deleteById(id);
    }
}