package com.javasampleapproach.xmlrestservice.model;

public class MortgageAccountDescription extends Description {
    private String lenderName;

    public MortgageAccountDescription(String accountType, String lenderName) {
        this.lenderName = lenderName;
        this.accountType = accountType;
    }

    public String toString(){
        String str = "mortgageAccount: lenderName: "+this.lenderName +", accountType: " + this.accountType;
        return str;
    }
}
