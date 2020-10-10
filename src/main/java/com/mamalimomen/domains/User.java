package com.mamalimomen.domains;

import com.mamalimomen.base.controllers.utilities.InValidDataException;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;
import java.io.Serializable;

@Embeddable
public class User implements Serializable {

    @Transient
    private static final long serialVersionUID = 1932703205649029402L;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(name = "about_me", nullable = false, columnDefinition = "text")
    private String aboutMe;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) throws InValidDataException {
        if (!firstName.matches("(\\w\\s?){3,}")) {
            throw new InValidDataException("FirstName");
        }
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) throws InValidDataException {
        if (!lastName.matches("(\\w\\s?){3,}")) {
            throw new InValidDataException("LastName");
        }
        this.lastName = lastName;
    }

    public String getFullName() {
        return getFirstName() + " " + getLastName();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) throws InValidDataException {
        if (!password.matches("[a-zA-Z0-9]{3,}")) {
            throw new InValidDataException("Password");
        }
        this.password = password;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    @Override
    public String toString() {
        return String.format("%s - %s - %s - %s%n", getCountry(), getCity(), getAvenue(), getPostalCode());
    }
}
