package bts.sio.azurimmo.service;

import bts.sio.azurimmo.model.Role;
import bts.sio.azurimmo.model.User;
import bts.sio.azurimmo.model.dto.UserDTO;
import bts.sio.azurimmo.model.mapper.UserMapper;
import bts.sio.azurimmo.repository.RoleRepository;
import bts.sio.azurimmo.repository.UserRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Récupérer tous les users
    public List<UserDTO> getAllUsersDTO() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Récupérer un user par ID
    public Optional<UserDTO> getUserDTO(Long id) {
        return userRepository.findById(id)
                .map(UserMapper::toDTO);
    }

    // Créer un user
    public UserDTO saveUserDTO(UserDTO dto) {
        User entity = UserMapper.toEntity(dto);

        // On encode le mot de passe avant de sauvegarder
        entity.setMotDePasse(passwordEncoder.encode(dto.getMotDePasse()));

        // On rattache le rôle si fourni
        if (dto.getRole() != null) {
            Optional<Role> role = roleRepository.findById(dto.getRole());
            role.ifPresent(entity::setRole);
        }

        User saved = userRepository.save(entity);
        return UserMapper.toDTO(saved);
    }

    // Supprimer un user
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public UserDTO login(String adresseMail, String motDePasse) {
        User user = userRepository.findByAdresseMail(adresseMail);

        if (user == null) {
            throw new RuntimeException("Email introuvable");
        }

        if (!passwordEncoder.matches(motDePasse, user.getMotDePasse())) {
            throw new RuntimeException("Mot de passe incorrect");
        }

        return UserMapper.toDTO(user);
    }
    public UserDTO register(UserDTO dto) {
        // Vérifie si l'email existe déjà
        User existing = userRepository.findByAdresseMail(dto.getAdresseMail());
        if (existing != null) {
            throw new RuntimeException("Cet email est déjà utilisé");
        }
        Role defaultRole = roleRepository.findByLibelleIgnoreCase("USER");
        if (defaultRole == null) {
            throw new RuntimeException("Role USER introuvable. Contactez l'administrateur.");
        }
        dto.setRole(defaultRole.getId());
        return saveUserDTO(dto); // saveUserDTO encode déjà le mot de passe
    }

}