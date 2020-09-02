package com.javasampleapproach.xmlrestservice.service;

import com.javasampleapproach.xmlrestservice.model.*;
import org.springframework.stereotype.Service;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.StringReader;
import java.util.*;

@Service
public class XMLProcessing {

    // This method returns a map containing all user accounts
    public Map<String, List<Account>> parse(String xml) {
        Map<String, List<Account>> map = new HashMap<>();
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            DefaultHandler handler = new DefaultHandler() {
                Account account;
                boolean accountNumber = false;
                boolean amount = false;
                boolean lenderName = false;
                boolean accountType = false;
                boolean creditCardNumber = false;
                boolean expirationDate = false;
                boolean issuer = false;

                //parser starts parsing a specific element inside the document
                public void startElement(String uri, String localName, String tagName, Attributes attributes) throws SAXException {
                    if(tagName.equals("bankAccount")) {
                        account = new BankAccount();
                    } else if(tagName.equals("creditCardAccount")) {
                        account = new CreditCardAccount();
                    } else if(tagName.equals("mortgageAccount")){
                        account = new MortgageAccount();
                    }
                    if(tagName.equalsIgnoreCase("ACCOUNTNUMBER")) accountNumber=true;

                    if (tagName.equalsIgnoreCase("AMOUNT")) amount = true;

                    if (tagName.equalsIgnoreCase("LENDERNAME")) lenderName = true;

                    if (tagName.equalsIgnoreCase("ACCOUNTTYPE")) accountType = true;

                    if (tagName.equalsIgnoreCase("CREDITCARDNUMBER")) creditCardNumber = true;

                    if (tagName.equalsIgnoreCase("EXPIRATIONDATE")) expirationDate = true;

                    if (tagName.equalsIgnoreCase("ISSUER")) issuer = true;

                }

                //parser ends parsing the specific element inside the document
                public void endElement(String uri, String localName, String tagName) throws SAXException {
                    if(tagName.equals("bankAccount") || tagName.equals("creditCardAccount") || tagName.equals("mortgageAccount")) {
                        if(map.containsKey(tagName)) {
                            List<Account> list = map.get(tagName);
                            list.add(account);
                            map.put(tagName, list);
                        } else {
                            List<Account> list = new ArrayList<>();
                            list.add(account);
                            map.put(tagName, list);
                        }
                    }
                }

                //reads the text value of the currently parsed element
                public void characters(char ch[], int start, int length) throws SAXException {
                    if (accountNumber) {
                        account.setAccountNumber(new String(ch, start, length));
                        accountNumber = false;
                    }
                    if (amount) {
                        account.setAmount(new String(ch, start, length));
                        amount = false;
                    }
                    if (lenderName) {
                        if(account instanceof BankAccount) {
                            ((BankAccount) account).setLenderName(new String(ch, start, length));
                        } else if(account instanceof MortgageAccount) {
                            ((MortgageAccount) account).setLenderName(new String(ch, start, length));
                        }
                        lenderName = false;
                    }
                    if (accountType) {
                        account.setAccountType(new String(ch, start, length));
                        accountType = false;
                    }
                    if (creditCardNumber) {
                        if(account instanceof CreditCardAccount) {
                            ((CreditCardAccount) account).setCreditCardNumber(new String(ch, start, length));
                        }
                        creditCardNumber = false;
                    }
                    if (expirationDate) {
                        if(account instanceof CreditCardAccount) {
                            ((CreditCardAccount) account).setExpDate(new String(ch, start, length));
                        }
                        expirationDate = false;
                    }
                    if (issuer) {
                        if(account instanceof CreditCardAccount) {
                            ((CreditCardAccount) account).setIssuer(new String(ch, start, length));
                        }
                        issuer = false;
                    }
                }
            };
            InputSource inputSource = new InputSource(new StringReader(xml));
            saxParser.parse(inputSource, handler);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return map;
    }
}
