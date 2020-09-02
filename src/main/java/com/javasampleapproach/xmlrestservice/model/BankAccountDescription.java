package com.javasampleapproach.xmlrestservice.model;

public class BankAccountDescription extends Description {
    private String lenderName;

    public BankAccountDescription(String accountType, String lenderName) {
        this.lenderName = lenderName;
        this.accountType = accountType;
    }

    public String toString(){
        String str = "bankAccount: bankAccountType: "+this.accountType +", bankName: " + this.lenderName;
        return str;
    }
}
