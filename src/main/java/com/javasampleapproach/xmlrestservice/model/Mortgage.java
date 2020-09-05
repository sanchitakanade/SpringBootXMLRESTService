package com.javasampleapproach.xmlrestservice.model;

public class Mortgage extends Account {
    private  String lender;

    public Mortgage(String accountNumber, String amount, String accountType, String lender) {
        this.accountNumber = accountNumber;
        this.amount = amount;
        this.accountType = accountType;
        this.lender = lender;
    }
    public Mortgage(){}

    public String getLender() {
        return lender;
    }

    public void setLender(String lender) {
        this.lender = lender;
    }

    @Override
    public Description createDescription() {
        return new MortgageDescription(this.accountType, this.lender);
    }
}
