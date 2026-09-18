package cl.curso.usuarios_service.controller;
import cl.curso.usuarios_service.model.User;
import cl.curso.usuarios_service.model.UserAddress;
import cl.curso.usuarios_service.model.UserRole;
import cl.curso.usuarios_service.service.UserService;
import org.springframework.http.HttpStatus;
import java.util.List;

import java.util.Map;
import java.util.Optional;

import jakarta.validation.constraints.Positive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/usuarios")
@Validated
public class UserController {

    /* private final UserService userService;*/

    @Autowired 
    private UserService userService;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public Optional <User> getUserById(
            @PathVariable
            @Positive(message = "no tienes a nadie registrado con esa informacion")
            Long id
    ) {
        return userService.getUserById(id);
    }

   @GetMapping("/userRoles")
public List<UserRole> getRoles() {
    return userService.getAllRoles();
}

@GetMapping("/userRoles/{id}")
public UserRole getRoleById(
        @PathVariable
        @Positive(message = "El ID debe ser mayor que cero")
        Long id
) {
    return userService.getRoleById(id)
            .orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Rol no encontrado: " + id
            ));
}

@GetMapping("/userAddresses")
public List<UserAddress> getAddresses() {
    return userService.getAllAddresses();
}

@GetMapping("/userAddresses/{id}")
public UserAddress getAddressById(
        @PathVariable
        @Positive(message = "El ID debe ser mayor que cero")
        Long id
) {
    return userService.getAddressById(id)
            .orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Dirección no encontrada: " + id
            ));
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