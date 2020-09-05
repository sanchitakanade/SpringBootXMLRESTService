package com.javasampleapproach.xmlrestservice.model;

public class CreditCard extends Account {
    private String creditCardNumber;
    private String expDate;
    private String issuer;

    public CreditCard(String accountNumber, String amount, String accountType, String creditCardNumber, String expDate, String issuer) {
        this.accountNumber = accountNumber;
        this.amount = amount;
        this.accountType = accountType;
        this.creditCardNumber = creditCardNumber;
        this.expDate = expDate;
        this.issuer = issuer;

    }
    public CreditCard() {}

    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    public void setExpDate(String expDate) {
        this.expDate = expDate;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public String getCreditCardNumber() { return creditCardNumber; }

    public String getExpDate() { return expDate; }

    public String getIssuer() { return issuer; }

    @Override
    public Description createDescription() {
        return new CreditCardDescription(this.accountType, this.creditCardNumber, this.expDate, this.issuer);
    }
}
