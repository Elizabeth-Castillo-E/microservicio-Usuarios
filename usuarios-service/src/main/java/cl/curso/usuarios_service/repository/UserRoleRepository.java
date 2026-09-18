package cl.curso.usuarios_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import cl.curso.usuarios_service.model.UserRole;

public interface UserRoleRepository
        extends JpaRepository<UserRole, Long> {
}