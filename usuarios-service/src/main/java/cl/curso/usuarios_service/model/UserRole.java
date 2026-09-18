package cl.curso.usuarios_service.model;

public class UserRole {

    private final long idUserRole;
    private final String userNameRole;
    private final String userDescriptionRole;
   

    public UserRole(long idUserRole, String userNameRole, String userDescriptionRole) {
        this.idUserRole = idUserRole;
        this.userNameRole = userNameRole;
        this.userDescriptionRole = userDescriptionRole;
    }

    public long getIdUserRole() {
        return idUserRole;
    }

    public String getUserNameRole() {
        return userNameRole;
    }

    public String getUserDescriptionRole() {
        return userDescriptionRole;
    }


}