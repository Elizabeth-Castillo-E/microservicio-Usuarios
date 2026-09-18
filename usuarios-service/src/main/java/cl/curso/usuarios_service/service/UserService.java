package cl.curso.usuarios_service.service;

import cl.curso.usuarios_service.model.User;
import cl.curso.usuarios_service.model.UserAddress;
import cl.curso.usuarios_service.model.UserRole;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@Service
public class UserService {

        private final List<UserRole> userRoles;
    
        private final List<UserAddress> userAddresses;
    
        private final List<User> users;

       
      public UserService() {
    UserRole administratorRole = new UserRole(
            1L,
            "ADMINISTRADOR",
            "Administra la tienda"
    );

    UserRole customerRole = new UserRole(
            2L,
            "CLIENTE",
            "Realiza compras"
    );

    UserRole deliveryRole = new UserRole(
            3L,
            "DESPACHADOR",
            "Realiza despachos"
    );
    UserRole supervisorRole = new UserRole(
            4L,
            "SUPERVISOR",
            "Supervisa las operaciones"
    );

    userRoles = List.of(
            administratorRole,
            customerRole,
            deliveryRole,
            supervisorRole
    );

    UserAddress address1 = new UserAddress(
            1L,
            "Avenida Providencia",
            "1234",
            "Santiago",
            "Región Metropolitana",
            "Chile"
    );

    UserAddress address2 = new UserAddress(
            2L,
            "Los Carrera",
            "850",
            "Concepción",
            "Región del Biobío",
            "Chile"
    );

    UserAddress address3 = new UserAddress(
            3L,
            "Avenida Brasil",
            "420",
            "Valparaíso",
            "Región de Valparaíso",
            "Chile"
    );

    userAddresses = List.of(
            address1,
            address2,
            address3
    );

    users = List.of(
            new User(
                    1L,
                    "12.345.678-5",
                    "Ana Pérez",
                    "ana@correo.cl",
                    "+56912345678",
                    
                    administratorRole,
                    List.of(address1,address2)
            ),
            new User(
                    2L,
                    "9.876.543-3",
                    "Bruno Soto",
                    "bruno@correo.cl",
                    "+56987654321",
                   
                    customerRole,
                    List.of(address2)
            ),
            new User(
                    3L,
                    "18.765.432-7",
                    "Carla Rojas",
                    "carla@correo.cl",
                    "+56933333333",
                   
                    deliveryRole,
                    List.of(address3)
            ),
            new User(
                    4L,
                    "20.123.456-7",
                    "David Gómez",
                    "david@correo.cl",
                    "+56944444444",
                   
                    supervisorRole,
                    List.of(address1)
            )
    );
}
   public List<User> findAllUsers() {
    return users;
}

public List<UserRole> findAllRoles() {
    return userRoles;
}

public List<UserAddress> findAllAddresses() {
    return userAddresses;
}

    public User findUserById(long id) {
    return findAllUsers().stream()
        .filter(user -> user.getIdUser() == id)
        .findFirst()
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND,
            "El usuario aun no se ha registrado en nuestras bases " + id
        ));
}

public UserRole findRoleById(long id) {
    return findAllRoles().stream()
        .filter(role -> role.getIdUserRole() == id)
        .findFirst()
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND,
            "Si no está, podemos crear este nuevo ROL " + id
        ));
}

public UserAddress findAddressById(long id) {
    return findAllAddresses().stream()
        .filter(address -> address.getIdUserAddress() == id)
        .findFirst()
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND,
            "Esta direccion no esta en el mapa " + id
        ));
}
}
