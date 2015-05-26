package net.timandersen.model;


import javax.persistence.*;

@Entity
@Table(name = "phone_numbers")
public class Phone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "phone_number_id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "phone_number")
    private String number;

    @Column(name = "phone_type")
    private String type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id", nullable = false)
    private Person person;

    public Phone(String number, String type, Person person) {
        this.number = number;
        this.type = type;
        this.person = person;
    }

    public Long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

