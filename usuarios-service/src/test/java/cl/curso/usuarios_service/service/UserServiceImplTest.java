package cl.curso.usuarios_service.service;

import cl.curso.usuarios_service.model.User;
import cl.curso.usuarios_service.model.UserRole;
import cl.curso.usuarios_service.repository.UserRepository;
import cl.curso.usuarios_service.repository.UserRoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository repository;

    @Mock
    private UserRoleRepository roleRepository;

    @InjectMocks
    private UserServiceImpl service;

    private User user;
    private UserRole role;

    @BeforeEach
    void setUp() {
        role = new UserRole("PACIENTE", "Usuario de prueba");
        role.setIdUserRole(1L);
        user = new User(50L, "12.345.678-5", "Ana Perez",
            "ana.prueba@example.com", "+56912345678", role, List.of());
    }

    @Test
    void testGetAllUsers() {
        List<User> expected = List.of(user);
        when(repository.findAll()).thenReturn(expected);

        assertEquals(expected, service.getAllUsers());
    }

    @Test
    void testGetUserById() {
        when(repository.findById(50L)).thenReturn(Optional.of(user));

        assertEquals(Optional.of(user), service.getUserById(50L));
    }

    @Test
    void testCreateUser() {
        user.setIdUser(null);
        when(roleRepository.findById(1L)).thenReturn(Optional.of(role));
        when(repository.save(user)).thenReturn(user);

        User result = service.saveUser(user);

        assertSame(user, result);
        assertSame(role, result.getUserRole());
        verify(repository).save(user);
    }

    @Test
    void testUpdateUserExist() {
        User incoming = new User(null, "12.345.678-5", "Ana Perez Actualizada",
            "ana.actualizada@example.com", "+56912345678", role, List.of());
        when(repository.findById(50L)).thenReturn(Optional.of(user));
        when(roleRepository.findById(1L)).thenReturn(Optional.of(role));
        when(repository.save(incoming)).thenReturn(incoming);

        User result = service.updateUser(50L, incoming);

        assertEquals(50L, result.getIdUser());
        assertEquals("Ana Perez Actualizada", result.getUserName());
        assertEquals("ana.actualizada@example.com", result.getUserEmail());
        verify(repository).save(incoming);
    }

    @Test
    void testUpdateUserNoExist() {
        when(repository.findById(50L)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
            () -> service.updateUser(50L, user));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        verify(repository, never()).save(any());
    }

    @Test
    void testDeleteUser() {
        when(repository.findById(50L)).thenReturn(Optional.of(user));

        service.deleteUser(50L);

        verify(repository).delete(user);
    }
}
