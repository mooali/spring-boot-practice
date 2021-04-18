package mo.bfh.entity;

import javax.persistence.*;

@Entity
public class Phone {

    @Id
    @SequenceGenerator(name = "phone_seq", sequenceName = "phone_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "phone_seq")
    private Integer id;
    private String phoneNumber;
    @Enumerated(EnumType.STRING)
    private PhoneType type;
    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.REMOVE}, fetch = FetchType.LAZY)
    private Employee employee;


    public Phone(String phoneNumber, PhoneType type) {
        this.phoneNumber = phoneNumber;
        this.type = type;
    }

    public Phone() {
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public PhoneType getType() {
        return type;
    }

    public void setType(PhoneType type) {
        this.type = type;
    }
}
