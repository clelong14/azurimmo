package bts.sio.azurimmo.service;

import bts.sio.azurimmo.model.Role;
import bts.sio.azurimmo.model.User;
import bts.sio.azurimmo.model.dto.UserDTO;
import bts.sio.azurimmo.model.mapper.UserMapper;
import bts.sio.azurimmo.repository.RoleRepository;
import bts.sio.azurimmo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<UserDTO> getAllUsersDTO() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<UserDTO> getUserDTO(Long id) {
        return userRepository.findById(id)
                .map(UserMapper::toDTO);
    }

    public UserDTO saveUserDTO(UserDTO dto) {
        if (userRepository.existsByAdresseMail(dto.getAdresseMail())) {
            throw new RuntimeException("Cet email est déjà utilisé");
        }

        User entity = UserMapper.toEntity(dto);

        entity.setMotDePasse(passwordEncoder.encode(dto.getMotDePasse()));

        if (dto.getRole() != null) {
            Optional<Role> role = roleRepository.findById(dto.getRole());
            role.ifPresent(entity::setRole);
        }

        User saved = userRepository.save(entity);
        return UserMapper.toDTO(saved);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public UserDTO login(String adresseMail, String motDePasse) {
        User user = userRepository.findByAdresseMail(adresseMail)
                .orElseThrow(() -> new RuntimeException("Identifiants invalides"));

        if (!passwordEncoder.matches(motDePasse, user.getMotDePasse())) {
            throw new RuntimeException("Identifiants invalides");
        }

        return UserMapper.toDTO(user);
    }

    public UserDTO register(UserDTO dto) {
        if (userRepository.existsByAdresseMail(dto.getAdresseMail())) {
            throw new RuntimeException("Cet email est déjà utilisé");
        }

        Role defaultRole = roleRepository.findByLibelleIgnoreCase("USER");
        if (defaultRole == null) {
            throw new RuntimeException("Role USER introuvable. Contactez l'administrateur.");
        }

        dto.setRole(defaultRole.getId());
        User entity = UserMapper.toEntity(dto);
        entity.setMotDePasse(passwordEncoder.encode(dto.getMotDePasse()));
        entity.setRole(defaultRole);
        User saved = userRepository.save(entity);
        return UserMapper.toDTO(saved);
    }

}