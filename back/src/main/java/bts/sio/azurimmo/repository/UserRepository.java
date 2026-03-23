package bts.sio.azurimmo.repository;

import bts.sio.azurimmo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByAdresseMail(String adresseMail);

	boolean existsByAdresseMail(String adresseMail);
}