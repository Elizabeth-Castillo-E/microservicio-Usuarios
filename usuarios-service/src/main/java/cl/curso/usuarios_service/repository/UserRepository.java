package cl.curso.usuarios_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import cl.curso.usuarios_service.model.User;
import cl.curso.usuarios_service.model.UserAddress;
import cl.curso.usuarios_service.model.UserRole;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserEmailIgnoreCase(String email);
}
