package com.mamalimomen.services.dtos;

import com.mamalimomen.base.services.dtos.BaseDTO;

import java.util.Date;

public class PostDTO extends BaseDTO<Long> {

    private String text;
    private Date fromDate;
    private Date tillDate;
    private String accountUsername;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getTillDate() {
        return tillDate;
    }

    public void setTillDate(Date tillDate) {
        this.tillDate = tillDate;
    }

    public String getAccountUsername() {
        return accountUsername;
    }

    public void setAccountUsername(String accountUsername) {
        this.accountUsername = accountUsername;
    }
}
