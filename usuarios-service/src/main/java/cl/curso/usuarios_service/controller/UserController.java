package cl.curso.usuarios_service.controller;
import cl.curso.usuarios_service.model.User;
import cl.curso.usuarios_service.model.UserAddress;
import cl.curso.usuarios_service.model.UserRole;
import cl.curso.usuarios_service.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

import java.util.Map;

import jakarta.validation.constraints.Positive;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/microservice/usuarios")
@Validated
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<User> getUsers() {
        return userService.findAllUsers();
    }

    @GetMapping("/users/{id}")
    public User getUserById(
            @PathVariable
            @Positive(message = "no tienes a nadie registrado con esa informacion")
            Long id
    ) {
        return userService.findUserById(id);
    }

    @GetMapping("/userRoles")
    public List<UserRole> getRoles() {
        return userService.findAllRoles();
    }

    @GetMapping("/userRoles/{id}")
    public UserRole getRoleById(
            @PathVariable
            @Positive(message = "El Rol que buscas solo no está")
            Long id
    ) {
        return userService.findRoleById(id);
    }

    @GetMapping("/userAddresses")
    public List<UserAddress> getAddresses() {
        return userService.findAllAddresses();
    }

    @GetMapping("/userAddresses/{id}")
    public UserAddress getAddressById(
            @PathVariable
            @Positive(message = "El ID debe ser mayor que cero")
            Long id
    ) {
        return userService.findAddressById(id);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(
            ResponseStatusException exception
    ) {
        return ResponseEntity
            .status(exception.getStatusCode())
            .body(Map.of(
                "mensaje", exception.getReason() != null
                    ? exception.getReason()
                    : "No se encontró el recurso solicitado",
                "estado", exception.getStatusCode().value()
            ));
    }
}