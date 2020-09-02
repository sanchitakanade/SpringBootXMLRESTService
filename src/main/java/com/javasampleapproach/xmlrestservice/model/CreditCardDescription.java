package com.javasampleapproach.xmlrestservice.model;

public class CreditCardDescription extends Description {
    private String creditCardNumber;
    private String expirationDate;
    private String issuer;

    public CreditCardDescription(String accountType, String creditCardNumber, String expirationDate, String issuer) {
        this.creditCardNumber = creditCardNumber;
        this.expirationDate = expirationDate;
        this.issuer = issuer;
        this.accountType = accountType;
    }

    public String toString(){
        String str = "creditCardAccount: creditCardNumber: "+this.creditCardNumber +", expirationDate: " + this.expirationDate
                +", issuer: " + this.issuer;
        return str;
    }
}
