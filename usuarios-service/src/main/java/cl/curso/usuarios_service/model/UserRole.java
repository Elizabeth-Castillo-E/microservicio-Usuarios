package cl.curso.usuarios_service.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "USER_ROLES")
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_USER_ROLE")
    private Long idUserRole;

    @Column(name = "ROLE_NAME", nullable = false, unique = true, length = 50)
    private String userNameRole;

    @Column(name = "ROLE_DESCRIPTION", length = 250)
    private String userDescriptionRole;

    protected UserRole() {
        
    }

    public UserRole(String userNameRole, String userDescriptionRole) {
        
        this.userNameRole = userNameRole;
        this.userDescriptionRole = userDescriptionRole;
    }

    public Long getIdUserRole() {
        return idUserRole;
    }

    public String getUserNameRole() {
        return userNameRole;
    }

    public String getUserDescriptionRole() {
        return userDescriptionRole;
    }

      public void setIdUserRole(Long idUserRole) {
        this.idUserRole = idUserRole;
    }
      public void setUserNameRole(String userNameRole) {
        this.userNameRole = userNameRole;
    }

    public void setUserDescriptionRole(String userDescriptionRole) {
        this.userDescriptionRole = userDescriptionRole;
    }
}
