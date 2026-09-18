package cl.curso.usuarios_service.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "USER_ADDRESSES")
public class UserAddress {
    @Id
    
    @Column(name = "ID_USER_ADDRESS")
    private Long idUserAddress;

    @Column(name = "STREET", nullable = false, length = 150)
    private String street;

    @Column(name = "STREET_NUMBER", nullable = false, length = 20)
    private String number;

    @Column(name = "CITY", nullable = false, length = 100)
    private String city;

    @Column(name = "REGION", nullable = false, length = 100)
    private String region;

    @Column(name = "COUNTRY", nullable = false, length = 100)
    private String country;

    @JsonBackReference
    @ManyToOne(optional = false)
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    protected UserAddress() {
        // Constructor requerido por JPA.
    }

    public UserAddress(
            long idUserAddress,
            String street,
            String number,
            String city,
            String region,
            String country
    ) {
        this.idUserAddress = idUserAddress;
        this.street = street;
        this.number = number;
        this.city = city;
        this.region = region;
        this.country = country;
    }

    public Long getIdUserAddress() {
        return idUserAddress;
    }

    public String getStreet() {
        return street;
    }

    public String getNumber() {
        return number;
    }

    public String getCity() {
        return city;
    }

    public String getRegion() {
        return region;
    }

    public String getCountry() {
        return country;
    }

      public void setIdUserAddress(Long idUserAddress) {
        this.idUserAddress = idUserAddress;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
