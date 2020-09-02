package com.javasampleapproach.xmlrestservice.model;

public class CreditCardAccount extends Account {
    private String creditCardNumber;
    private String expDate;
    private String issuer;

    public CreditCardAccount(String accountNumber, String amount, String accountType, String creditCardNumber, String expDate, String issuer) {
        this.accountNumber = accountNumber;
        this.amount = amount;
        this.accountType = accountType;
        this.creditCardNumber = creditCardNumber;
        this.expDate = expDate;
        this.issuer = issuer;
    }
    public CreditCardAccount() {}

    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    public void setExpDate(String expDate) {
        this.expDate = expDate;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    @Override
    public Description createDescription() {
        return new CreditCardDescription(this.accountType, this.creditCardNumber, this.expDate, this.issuer);
    }
}
