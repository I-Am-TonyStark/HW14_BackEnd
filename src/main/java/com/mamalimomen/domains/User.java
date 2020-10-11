package com.mamalimomen.domains;

import com.mamalimomen.base.controllers.utilities.InValidDataException;
import com.mamalimomen.dtos.UserDTO;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;
import java.io.Serializable;

@Embeddable
public final class User implements Serializable {

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

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        User user = (User) obj;
        return this.hashCode() == user.hashCode();
    }

    public void copyMeFrom(UserDTO userDTO) {
        this.setFirstName(userDTO.getFirstName());
        this.setLastName(userDTO.getLastName());
        this.setUsername(userDTO.getUsername());
        this.setPassword(userDTO.getPassword());
        this.setAboutMe(userDTO.getAboutMe());
    }

    public UserDTO copyMeTo() {
        UserDTO dto = new UserDTO();
        try {
            dto.setFirstName(this.getFirstName());
            dto.setLastName(this.getLastName());
            dto.setUsername(this.getUsername());
            dto.setPassword(this.getPassword());
            dto.setAboutMe(this.getAboutMe());
        } catch (InValidDataException e) {
            e.printStackTrace();
        }
        return dto;
    }
}
