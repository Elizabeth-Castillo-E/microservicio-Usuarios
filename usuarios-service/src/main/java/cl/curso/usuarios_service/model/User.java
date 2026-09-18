package cl.curso.usuarios_service.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "USERS")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_USER")
    private Long idUser;

    @Column(name = "USER_RUT", nullable = false, unique = true, length = 12)
    private String userRut;

    @Column(name = "USER_NAME", nullable = false, length = 120)
    private String userName;

    @Column(name = "USER_EMAIL", nullable = false, unique = true, length = 150)
    private String userEmail;

    @Column(name = "USER_PHONE", length = 20)
    private String userPhone;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "ROLE_ID", nullable = false)
    private UserRole userRole;

    @JsonManagedReference
    @OneToMany(
        mappedBy = "user",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private List<UserAddress> userAddresses = new ArrayList<>();

    protected User() {
        // Constructor requerido por JPA.
    }

    public User(Long idUser, 
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

    public Long getIdUser() {
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

   public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }
    public void setUserRut(String userRut) {
        this.userRut = userRut;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
    public void setUserPhone(String userPhone) {
        this.userPhone= userPhone;
    }
   
 public void setUserRole (UserRole userRole) {
    this.userRole = userRole;
}

public void setUserAddresses(List<UserAddress> userAddresses) {
    this.userAddresses.clear();

    if (userAddresses != null) {
        userAddresses.forEach(this::addUserAddress);
    }
}

public void addUserAddress(UserAddress userAddress) {
    this.userAddresses.add(userAddress);
    userAddress.setUser(this);
}

public void removeUserAddress(UserAddress userAddress) {
    this.userAddresses.remove(userAddress);
    userAddress.setUser(null);
}
}
