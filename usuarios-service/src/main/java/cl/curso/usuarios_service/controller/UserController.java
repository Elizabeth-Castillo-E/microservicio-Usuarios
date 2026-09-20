package cl.curso.usuarios_service.controller;

import cl.curso.usuarios_service.model.User;
import cl.curso.usuarios_service.model.UserAddress;
import cl.curso.usuarios_service.model.UserRole;
import cl.curso.usuarios_service.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/usuarios")
@Validated
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAllUsers() { return userService.getAllUsers(); }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable @Positive(message = "El ID debe ser mayor que cero") Long id) {
        return userService.getUserById(id).orElseThrow(() ->
            new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado: " + id));
    }

    @PostMapping
    public User createUser(@Valid @RequestBody User user) { return userService.saveUser(user); }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable @Positive(message = "El ID debe ser mayor que cero") Long id,
                           @Valid @RequestBody User user) {
        return userService.updateUser(id, user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable @Positive(message = "El ID debe ser mayor que cero") Long id) {
        userService.deleteUser(id);
    }

    @PostMapping("/{id}/addresses")
    public User addAddressToUser(@PathVariable @Positive(message = "El ID debe ser mayor que cero") Long id,
                                 @Valid @RequestBody UserAddress address) {
        return userService.addAddressToUser(id, address);
    }

    @GetMapping("/{id}/addresses")
    public List<UserAddress> getAddressesFromUser(
            @PathVariable @Positive(message = "El ID debe ser mayor que cero") Long id
    ) {
        return userService.getUserById(id)
                .map(User::getUserAddresses)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado: " + id));
    }

    @GetMapping("/{userId}/addresses/{addressId}")
    public UserAddress getAddressById(
            @PathVariable @Positive(message = "El ID del usuario debe ser mayor que cero") Long userId,
            @PathVariable @Positive(message = "El ID de la dirección debe ser mayor que cero") Long addressId
    ) {
        User user = userService.getUserById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado: " + userId));
        return user.getUserAddresses().stream()
                .filter(address -> addressId.equals(address.getIdUserAddress()))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Dirección no encontrada para el usuario: " + userId
                ));
    }

    @PutMapping("/{userId}/addresses/{addressId}")
    public User updateAddressFromUser(
            @PathVariable @Positive(message = "El ID del usuario debe ser mayor que cero") Long userId,
            @PathVariable @Positive(message = "El ID de la dirección debe ser mayor que cero") Long addressId,
            @Valid @RequestBody UserAddress address
    ) {
        return userService.updateAddressFromUser(userId, addressId, address);
    }

    @DeleteMapping("/{userId}/addresses/{addressId}")
    public User removeAddressFromUser(
            @PathVariable @Positive(message = "El ID del usuario debe ser mayor que cero") Long userId,
            @PathVariable @Positive(message = "El ID de la dirección debe ser mayor que cero") Long addressId
    ) {
        return userService.removeAddressFromUser(userId, addressId);
    }

    @GetMapping("/userRoles")
    public List<UserRole> getRoles() { return userService.getAllRoles(); }

    @GetMapping("/userRoles/{id}")
    public UserRole getRoleById(@PathVariable @Positive(message = "El ID debe ser mayor que cero") Long id) {
        return userService.getRoleById(id).orElseThrow(() ->
            new ResponseStatusException(HttpStatus.NOT_FOUND, "Rol no encontrado: " + id));
    }

    @PostMapping("/userRoles")
    public UserRole createUserRole(@Valid @RequestBody UserRole userRole) {
        return userService.saveUserRole(userRole);
    }

    @PutMapping("/userRoles/{id}")
    public UserRole updateUserRole(@PathVariable @Positive(message = "El ID debe ser mayor que cero") Long id,
                                   @Valid @RequestBody UserRole userRole) {
        return userService.updateUserRole(id, userRole);
    }

    @DeleteMapping("/userRoles/{id}")
    public void deleteUserRole(@PathVariable @Positive(message = "El ID debe ser mayor que cero") Long id) {
        userService.deleteUserRole(id);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(ResponseStatusException exception) {
        return ResponseEntity.status(exception.getStatusCode()).body(Map.of(
            "mensaje", exception.getReason() == null ? "No se encontró el recurso solicitado" : exception.getReason(),
            "estado", exception.getStatusCode().value()
        ));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new LinkedHashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(error ->
            errors.put(error.getField(), error.getDefaultMessage())
        );
        return ResponseEntity.badRequest().body(Map.of(
            "mensaje", "La solicitud contiene campos inválidos",
            "errores", errors
        ));
    }
}
