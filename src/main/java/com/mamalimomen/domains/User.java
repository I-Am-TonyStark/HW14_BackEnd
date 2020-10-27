package com.mamalimomen.domains;

import com.mamalimomen.base.controllers.utilities.InValidDataException;
import com.mamalimomen.base.controllers.utilities.SecurityManager;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;
import java.io.Serializable;

@Embeddable
public final class User implements Serializable {

    @Transient
    private static final long serialVersionUID = 1932703205649029402L;

    @Column(name = "first_name", nullable = false)
    private String firstName = "";

    @Column(name = "last_name", nullable = false)
    private String lastName = "";

    @Column(nullable = false)
    private String username = "";

    @Column(nullable = false)
    private String password = "";

    @Column(name = "about_me", nullable = false, columnDefinition = "text")
    private String aboutMe = "";

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) throws InValidDataException {
        if (!username.matches("^(?!.*\\.\\.)(?!.*\\.$)[^\\W][\\w.]{0,29}$")) {
            throw new InValidDataException("Username");
        }
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) throws InValidDataException {
        if (!password.matches("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$")) {
            throw new InValidDataException("Password");
        }
        this.password = SecurityManager.getPasswordHash(password);
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    public String getFullName() {
        return getFirstName() + " " + getLastName();
    }

    public void updateUserInformation(User user) {
        try {
            this.setUsername(user.getUsername().isEmpty() ? this.getUsername() : user.getUsername());
            this.setFirstName(user.getFirstName().isEmpty() ? this.getFirstName() : user.getFirstName());
            this.setLastName(user.getLastName().isEmpty() ? this.getLastName() : user.getLastName());
            this.setAboutMe(user.getAboutMe().isEmpty() ? this.getAboutMe() : user.getAboutMe());
        } catch (InValidDataException ignored) {
        }
    }

    @Override
    public String toString() {
        return String.format("%s%n%s%n%s%n", getUsername(), getFullName(), getAboutMe());
    }
}
