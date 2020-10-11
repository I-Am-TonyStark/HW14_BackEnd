package com.mamalimomen.services.dtos;

import com.mamalimomen.base.services.dtos.BaseDTO;

public class AccountDTO extends BaseDTO<Long> {

    private String username;
    private String ownerFirstName;
    private String ownerLastName;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getOwnerFirstName() {
        return ownerFirstName;
    }

    public void setOwnerFirstName(String ownerFirstName) {
        this.ownerFirstName = ownerFirstName;
    }

    public String getOwnerLastName() {
        return ownerLastName;
    }

    public void setOwnerLastName(String ownerLastName) {
        this.ownerLastName = ownerLastName;
    }
}
