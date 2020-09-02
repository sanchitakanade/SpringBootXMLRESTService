package com.javasampleapproach.xmlrestservice.model;

public abstract class Account {
    String accountNumber;
    String amount;
    String accountType;

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getAmount() {
        return amount;
    }

    public abstract Description createDescription();
}
