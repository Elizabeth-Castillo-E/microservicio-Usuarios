package cl.curso.usuarios_service.controller;

import cl.curso.usuarios_service.model.User;
import cl.curso.usuarios_service.model.UserRole;
import cl.curso.usuarios_service.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService service;

    @Autowired
    private ObjectMapper mapper;

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
    void testGetAllUsers() throws Exception {
        when(service.getAllUsers()).thenReturn(List.of(user));

        mockMvc.perform(get("/usuarios"))
            .andExpect(status().isOk())
            .andExpect(content().json(mapper.writeValueAsString(List.of(user))));
    }

    @Test
    void testGetUserById() throws Exception {
        when(service.getUserById(50L)).thenReturn(Optional.of(user));

        mockMvc.perform(get("/usuarios/50"))
            .andExpect(status().isOk())
            .andExpect(content().json(mapper.writeValueAsString(user)));
    }

    @Test
    void testCreateUser() throws Exception {
        when(service.saveUser(any(User.class))).thenReturn(user);

        mockMvc.perform(post("/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(user)))
            .andExpect(status().isOk())
            .andExpect(content().json(mapper.writeValueAsString(user)));
        verify(service).saveUser(any(User.class));
    }

    @Test
    void testUpdateUserExist() throws Exception {
        user.setUserName("Ana Perez Actualizada");
        when(service.updateUser(eq(50L), any(User.class))).thenReturn(user);

        mockMvc.perform(put("/usuarios/50")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(user)))
            .andExpect(status().isOk())
            .andExpect(content().json(mapper.writeValueAsString(user)));
        verify(service).updateUser(eq(50L), any(User.class));
    }

    @Test
    void testDeleteUser() throws Exception {
        mockMvc.perform(delete("/usuarios/50"))
            .andExpect(status().isOk());

        verify(service).deleteUser(50L);
    }
}
