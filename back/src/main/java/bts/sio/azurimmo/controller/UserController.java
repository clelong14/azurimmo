package bts.sio.azurimmo.controller;

import bts.sio.azurimmo.model.User;
import bts.sio.azurimmo.model.dto.UserDTO;
import bts.sio.azurimmo.repository.UserRepository;
import bts.sio.azurimmo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/login")
@Tag(name = "Users", description = "Gestion des utilisateurs")
public class UserController {

    @PostMapping("/login")
    @Operation(summary = "Connexion d'un utilisateur")
    public ResponseEntity<?> login(@RequestBody UserDTO dto) {
        try {
            UserDTO user = userService.login(dto.getAdresseMail(), dto.getMotDePasse());
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }
    @PostMapping("/register")
    @Operation(summary = "Inscription d'un utilisateur")
    public ResponseEntity<?> register(@RequestBody UserDTO dto) {
        try {
            UserDTO saved = userService.register(dto);
            return ResponseEntity.status(201).body(saved);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }


    @Autowired
    private UserService userService;

    @GetMapping("/all")
    @Operation(summary = "Récupère tous les utilisateurs")
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsersDTO();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Récupère un utilisateur par son ID")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id) {
        return userService.getUserDTO(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Créer un utilisateur")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO dto) {
        UserDTO saved = userService.saveUserDTO(dto);
        return ResponseEntity.status(201).body(saved);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un utilisateur")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}