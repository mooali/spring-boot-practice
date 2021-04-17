package mo.bfh.entity;

import javax.persistence.*;

@Entity
public class Address {

    @Id
    @SequenceGenerator(name = "address_seq", sequenceName = "address_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "address_seq")
    private Integer id;
    private String street;
    private String city;
    private  String state;
    private  String zip;

    public Address() {
    }

    public Address(String street, String city, String state, String zip) {
        this.street = street;
        this.city = city;
        this.state = state;
        this.zip = zip;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }
}
