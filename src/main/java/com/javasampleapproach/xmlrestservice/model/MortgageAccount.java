package com.javasampleapproach.xmlrestservice.model;

public class MortgageAccount extends Account{
    private  String lenderName;

    public MortgageAccount(String accountNumber, String amount, String accountType, String lenderName) {
        this.accountNumber = accountNumber;
        this.amount = amount;
        this.accountType = accountType;
        this.lenderName = lenderName;
    }
    public MortgageAccount(){}

    public void setLenderName(String lenderName) {
        this.lenderName = lenderName;
    }

    @Override
    public Description createDescription() {
        return new MortgageAccountDescription(this.accountType, this.lenderName);
    }
}
