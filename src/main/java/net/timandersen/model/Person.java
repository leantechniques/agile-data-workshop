package net.timandersen.model;

import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "people")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "person_id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "address_line_1")
    private String addressLine1;

    @Column(name = "address_line_2")
    private String addressLine2;

    @Column(name = "city")
    private String city;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "state")
    private String state;

    @Column(name = "zip_code")
    private String zipCode;

    @OneToMany(mappedBy = "person", orphanRemoval = true)
    @Cascade(value = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set<Phone> phones = new HashSet<Phone>();

    @OneToMany(mappedBy = "person", orphanRemoval = true)
    @Cascade(value = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set<Email> emails = new HashSet<Email>();

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Set<Email> getEmails() {
        return emails;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Long getId() {
        return id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<Phone> getPhones() {
        return phones;
    }

    public void setPhones(Set<Phone> phones) {
        this.phones = phones;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public void addPhone(Phone phone) {
        phones.add(phone);
    }

    public void addEmail(Email email) {
        emails.add(email);
    }

}
