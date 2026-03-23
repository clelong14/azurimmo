package bts.sio.azurimmo.repository;

import bts.sio.azurimmo.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByLibelle(String libelle);
    Role findByLibelleIgnoreCase(String libelle);
}