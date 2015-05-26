package net.timandersen.model;

import javax.persistence.*;

@Entity
@Table(name = "email")
public class Email {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "email_id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "email_address")
    private String address;

    @Column(name = "email_type")
    private String type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id", nullable = false)
    private Person person;

    public Email(String address, String type, Person person) {
        this.address = address;
        this.type = type;
        this.person = person;
    }

    public String getAddress() {
        return address;
    }

    public String getType() {
        return type;
    }

    public Person getPerson() {
        return person;
    }

}
