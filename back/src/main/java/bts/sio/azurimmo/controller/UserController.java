package bts.sio.azurimmo.controller;

import bts.sio.azurimmo.model.dto.UserDTO;
import bts.sio.azurimmo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping
@Tag(name = "Users", description = "Gestion des utilisateurs")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping({"/auth/login", "/login/login"})
    @Operation(summary = "Connexion d'un utilisateur")
    public ResponseEntity<UserDTO> login(@Valid @RequestBody UserDTO dto) {
        try {
            return ResponseEntity.ok(userService.login(dto.getAdresseMail(), dto.getMotDePasse()));
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @PostMapping({"/auth/register", "/login/register"})
    @Operation(summary = "Inscription d'un utilisateur")
    public ResponseEntity<UserDTO> register(@Valid @RequestBody UserDTO dto) {
        try {
            UserDTO saved = userService.register(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @GetMapping({"/users", "/users/all", "/login/all"})
    @Operation(summary = "Récupère tous les utilisateurs")
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsersDTO();
    }

    @GetMapping({"/users/{id}", "/login/{id}"})
    @Operation(summary = "Récupère un utilisateur par son ID")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id) {
        return userService.getUserDTO(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping({"/users", "/login"})
    @Operation(summary = "Créer un utilisateur")
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO dto) {
        UserDTO saved = userService.saveUserDTO(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @DeleteMapping({"/users/{id}", "/login/{id}"})
    @Operation(summary = "Supprimer un utilisateur")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}