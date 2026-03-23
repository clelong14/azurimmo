package bts.sio.azurimmo.controller;

import bts.sio.azurimmo.model.dto.RoleDTO;
import bts.sio.azurimmo.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
@Tag(name = "Roles", description = "Gestion des rôles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping("/all")
    @Operation(summary = "Récupère tous les rôles")
    public List<RoleDTO> getAllRoles() {
        return roleService.getAllRolesDTO();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Récupère un rôle par son ID")
    public ResponseEntity<RoleDTO> getRole(@PathVariable Long id) {
        return roleService.getRoleDTO(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Créer un rôle")
    public ResponseEntity<RoleDTO> createRole(@RequestBody RoleDTO dto) {
        RoleDTO saved = roleService.saveRoleDTO(dto);
        return ResponseEntity.status(201).body(saved);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un rôle")
    public ResponseEntity<Void> deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);
        return ResponseEntity.noContent().build();
    }
}