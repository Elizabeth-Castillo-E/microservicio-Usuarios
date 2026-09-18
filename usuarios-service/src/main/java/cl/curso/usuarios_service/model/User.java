package cl.curso.usuarios_service.model;


import java.util.List;

public class User {
    private final long idUser;
    private final String userRut;
    private final String userName;
    private final String userEmail;
    private final String userPhone;

    private final UserRole userRole;
    private final List<UserAddress> userAddresses;
   
    public User(long idUser, 
                String userRut, 
                String userName, 
                String userEmail, 
                String userPhone,
                UserRole userRole,
                List<UserAddress> userAddresses) {
        this.idUser = idUser;
        this.userRut = userRut;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPhone = userPhone;
        this.userRole = userRole;
        this.userAddresses = userAddresses;
    }

    public long getIdUser() {
        return idUser;
    }
    public String getUserRut() {
        return userRut;
    }
    public String getUserName() {
        return userName;
    }
    public String getUserEmail() {
        return userEmail;
    }
    public String getUserPhone() {
        return userPhone;
    }
   
 public UserRole getUserRole() {
        return userRole;
    }
    public List<UserAddress> getUserAddresses() {
        return userAddresses;
    }
}
