package cl.curso.usuarios_service.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import cl.curso.usuarios_service.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUserRut(String userRut);
    boolean existsByUserEmail(String userEmail);
    boolean existsByUserRutAndIdUserNot(String userRut, Long idUser);
    boolean existsByUserEmailAndIdUserNot(String userEmail, Long idUser);
}
