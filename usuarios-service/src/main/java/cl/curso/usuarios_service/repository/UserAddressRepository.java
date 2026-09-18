package cl.curso.usuarios_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import cl.curso.usuarios_service.model.UserAddress;

public interface UserAddressRepository
        extends JpaRepository<UserAddress, Long> {
}