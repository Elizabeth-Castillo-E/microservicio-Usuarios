/*package cl.curso.usuarios_service.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import cl.curso.usuarios_service.model.User;
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

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import cl.curso.usuarios_service.model.User;
import cl.curso.usuarios_service.model.UserAddress;
import cl.curso.usuarios_service.model.UserRole;
import cl.curso.usuarios_service.repository.UserAddressRepository;
import cl.curso.usuarios_service.repository.UserRepository;
import cl.curso.usuarios_service.repository.UserRoleRepository;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final UserAddressRepository userAddressRepository;

    public UserServiceImpl(
            UserRepository userRepository,
            UserRoleRepository userRoleRepository,
            UserAddressRepository userAddressRepository
    ) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.userAddressRepository = userAddressRepository;
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
    public List<UserRole> getAllRoles() {
        return userRoleRepository.findAll();
    }

    @Override
    public Optional<UserRole> getRoleById(Long id) {
        return userRoleRepository.findById(id);
    }

    @Override
    public List<UserAddress> getAllAddresses() {
        return userAddressRepository.findAll();
    }

    @Override
    public Optional<UserAddress> getAddressById(Long id) {
        return userAddressRepository.findById(id);
    }
}