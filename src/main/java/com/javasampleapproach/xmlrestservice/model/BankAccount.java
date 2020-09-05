package com.javasampleapproach.xmlrestservice.model;

public class BankAccount extends Account {
    private  String lenderName;
    public BankAccount(String accountNumber, String amount, String accountType, String lenderName) {
        this.accountNumber = accountNumber;
        this.amount = amount;
        this.accountType = accountType;
        this.lenderName = lenderName;

    }
    public BankAccount(){}
    public String getLenderName() {
        return lenderName;
    }
    public void setLenderName(String lenderName) {
        this.lenderName = lenderName;
    }

    @Override
    public Description createDescription() {
        return new BankAccountDescription(this.accountType, this.lenderName);
    }
}
