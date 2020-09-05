package com.javasampleapproach.xmlrestservice.model;

public class MortgageDescription extends Description {
    private String lenderName;

    public MortgageDescription(String accountType, String lenderName) {
        this.lenderName = lenderName;
        this.accountType = accountType;
    }

    public String toString(){
        String str = "mortgageAccount: lenderName: "+this.lenderName +", accountType: " + this.accountType;
        return str;
    }
}
