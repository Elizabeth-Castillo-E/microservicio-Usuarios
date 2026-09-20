/*package cl.curso.usuarios_service.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import cl.curso.usuarios_service.model.User;
import cl.curso.usuarios_service.model.UserAddress;
import cl.curso.usuarios_service.repository.UserRepository;
import java.util.List;
import java.util.Optional;

@Service 

public class UserServiceImpl implements UserService{

    @Autowired 

    public  UserRepository userRepository;

    @Override 
    public List<User> getAllUsers(){
        return  userRepository.findAll();
    }
    
    @Override 
    public Optional<User> getUserById(Long id){
        return userRepository.findById(id);
    }


} */
package cl.curso.usuarios_service.service;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.Optional;

import cl.curso.usuarios_service.model.User;
import cl.curso.usuarios_service.model.UserAddress;
import cl.curso.usuarios_service.model.UserRole;
import cl.curso.usuarios_service.repository.UserRepository;
import cl.curso.usuarios_service.repository.UserRoleRepository;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;

    public UserServiceImpl(
            UserRepository userRepository,
            UserRoleRepository userRoleRepository
    ) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }
    
    @Override 
    public User saveUser(User user){
        validateUniqueUserFields(user, null);
        linkExistingRole(user);
        linkUserAddresses(user);
        return userRepository.save(user);
    }
    @Override 
    public User updateUser(Long id, User user){
        requireUser(id);
        validateUniqueUserFields(user, id);
        user.setIdUser(id);
        linkExistingRole(user);
        linkUserAddresses(user);
        return userRepository.save(user);
    }
    @Override 
    public void deleteUser(Long id){
        userRepository.delete(requireUser(id));
    }

    @Override
    public User addAddressToUser(Long userId, UserAddress address) {
        User user = findUserOrThrow(userId);
        user.addUserAddress(address);
        return userRepository.save(user);
    }

    @Override
    public User updateAddressFromUser(Long userId, Long addressId, UserAddress newAddress) {
        User user = findUserOrThrow(userId);
        UserAddress address = user.getUserAddresses().stream()
            .filter(item -> addressId.equals(item.getIdUserAddress()))
            .findFirst()
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Dirección no encontrada para el usuario: " + userId));
        address.setStreet(newAddress.getStreet());
        address.setNumber(newAddress.getNumber());
        address.setCity(newAddress.getCity());
        address.setRegion(newAddress.getRegion());
        address.setCountry(newAddress.getCountry());
        return userRepository.save(user);
    }

    @Override
    public User removeAddressFromUser(Long userId, Long addressId) {
        User user = findUserOrThrow(userId);
        UserAddress address = user.getUserAddresses().stream()
            .filter(item -> addressId.equals(item.getIdUserAddress()))
            .findFirst()
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "La dirección no pertenece al usuario: " + userId
            ));
        user.removeUserAddress(address);
        return userRepository.save(user);
    }

    @Override
    public List<UserRole> getAllRoles() {
        return userRoleRepository.findAll();
    }

    @Override
    public Optional<UserRole> getRoleById(Long id) {
        return userRoleRepository.findById(id);
    }
    @Override 
    public UserRole saveUserRole (UserRole userRole){
        return userRoleRepository.save(userRole);
    }
    @Override 
    public UserRole updateUserRole(Long id, UserRole userRole){
        requireRole(id);
        userRole.setIdUserRole(id);
        return userRoleRepository.save(userRole);
    }
    @Override 
    public void deleteUserRole(Long id){
        userRoleRepository.delete(requireRole(id));
    }

    private void linkExistingRole(User user) {
        if (user.getUserRole() == null || user.getUserRole().getIdUserRole() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Debe indicar el ID de un rol existente");
        }
        user.setUserRole(requireRole(user.getUserRole().getIdUserRole()));
    }

    private void validateUniqueUserFields(User user, Long currentUserId) {
        boolean rutExists = currentUserId == null
            ? userRepository.existsByUserRut(user.getUserRut())
            : userRepository.existsByUserRutAndIdUserNot(user.getUserRut(), currentUserId);
        if (rutExists) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                "Ya existe un usuario registrado con el RUT: " + user.getUserRut());
        }

        boolean emailExists = currentUserId == null
            ? userRepository.existsByUserEmail(user.getUserEmail())
            : userRepository.existsByUserEmailAndIdUserNot(user.getUserEmail(), currentUserId);
        if (emailExists) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                "Ya existe un usuario registrado con el correo: " + user.getUserEmail());
        }
    }
    private void linkUserAddresses(User user) {
        user.getUserAddresses().forEach(address -> address.setUser(user));
    }

    private User findUserOrThrow(Long id) {
        return requireUser(id);
    }

    private User requireUser(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND,
            "Usuario no encontrado: " + id
        ));
    }

    private UserRole requireRole(Long id) {
        return userRoleRepository.findById(id).orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND,
            "Rol no encontrado: " + id
        ));
    }
}
