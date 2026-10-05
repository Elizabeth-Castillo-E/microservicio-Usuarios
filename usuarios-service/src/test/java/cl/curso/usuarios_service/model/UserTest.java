package cl.curso.usuarios_service.model;

import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

class UserTest {

    @Test
    void testGetterAndSetters() {
        User user = new User();
        UserRole role = new UserRole("PACIENTE", "Usuario de prueba");
        UserAddress address = new UserAddress(null, "Calle Ejemplo", "100",
            "Santiago", "Metropolitana", "Chile");

        user.setIdUser(50L);
        user.setUserRut("12.345.678-5");
        user.setUserName("Ana Perez");
        user.setUserEmail("ana.prueba@example.com");
        user.setUserPhone("+56912345678");
        user.setUserRole(role);
        user.setUserAddresses(List.of(address));

        assertEquals(50L, user.getIdUser());
        assertEquals("12.345.678-5", user.getUserRut());
        assertEquals("Ana Perez", user.getUserName());
        assertEquals("ana.prueba@example.com", user.getUserEmail());
        assertEquals("+56912345678", user.getUserPhone());
        assertSame(role, user.getUserRole());
        assertEquals(List.of(address), user.getUserAddresses());
        assertSame(user, address.getUser());
    }
}
