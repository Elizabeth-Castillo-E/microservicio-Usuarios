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
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

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
    @NotBlank(message = "El campo RUT es obligatorio")
    @Pattern(
        regexp = "^\\d{1,2}\\.\\d{3}\\.\\d{3}-[0-9Kk]$",
        message = "El RUT debe tener el formato 12.345.678-9"
    )
    private String userRut;

    @Column(name = "USER_NAME", nullable = false, length = 120)
    @NotBlank(message = "El campo Nombre es requerido")
    @Size(max = 120, message = "El nombre no puede superar 120 caracteres")
    private String userName;

    @Column(name = "USER_EMAIL", nullable = false, unique = true, length = 150)
    @NotBlank(message = "El correo electrónico es requerido")
    @Email(message = "El correo electrónico no tiene un formato válido")
    @Size(max = 150, message = "El correo electrónico no puede superar 150 caracteres")
    private String userEmail;

    @Column(name = "USER_PHONE", length = 9)
    @Pattern(
        regexp = "^$|^\\+?[0-9]{9}$",
        message = "El teléfono debe contener 9 dígitos"
    )
    private String userPhone;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "ROLE_ID", nullable = false)
    @NotNull(message = "Debe indicar un rol para el usuario")
    private UserRole userRole;

    @JsonManagedReference
    @OneToMany(
        mappedBy = "user",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    @Valid
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
