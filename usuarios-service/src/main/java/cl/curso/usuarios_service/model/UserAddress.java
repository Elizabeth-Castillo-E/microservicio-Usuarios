package cl.curso.usuarios_service.model;


public class UserAddress {

    private final long idUserAddress;
    private final String street;
    private final String number;
    private final String city;
    private final String region;
    private final String country;

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

    public long getIdUserAddress() {
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
}