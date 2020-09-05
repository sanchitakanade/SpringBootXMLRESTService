package com.javasampleapproach.xmlrestservice.service;

import com.javasampleapproach.xmlrestservice.model.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
                        account = new CreditCard();
                    } else if(tagName.equals("mortgageAccount")){
                        account = new Mortgage();
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
                        } else if(account instanceof Mortgage) {
                            ((Mortgage) account).setLender(new String(ch, start, length));
                        }
                        lenderName = false;
                    }
                    if (accountType) {
                        account.setAccountType(new String(ch, start, length));
                        accountType = false;
                    }
                    if (creditCardNumber) {
                        if(account instanceof CreditCard) {
                            ((CreditCard) account).setCreditCardNumber(new String(ch, start, length));
                        }
                        creditCardNumber = false;
                    }
                    if (expirationDate) {
                        if(account instanceof CreditCard) {
                            ((CreditCard) account).setExpDate(new String(ch, start, length));
                        }
                        expirationDate = false;
                    }
                    if (issuer) {
                        if(account instanceof CreditCard) {
                            ((CreditCard) account).setIssuer(new String(ch, start, length));
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

    public void validation(Map<String, List<Account>> map) throws Exception {
        String nonDigits = "[^\\d]";
        String specialChars = "[$~`@!#%^*()<>?/+|{}\\[\\]\":;=',]";
        if(map.containsKey("bankAccount")) {
            List<Account> bankAccounts = map.get("bankAccount");
            for (Account account : bankAccounts) {
                isNullOrEmpty(account.getAmount(),"bankAccount");
                isNullOrEmpty(account.getAccountNumber(),"bankAccount");
                isNullOrEmpty(account.getAccountType(),"bankAccount");
                isNullOrEmpty(((BankAccount) account).getLenderName(),"bankAccount");

                containsPattern(account.getAmount(),nonDigits, "amount", "bankAccount");
                containsPattern(account.getAccountNumber(),nonDigits, "account number", "bankAccount");
                containsPattern(account.getAccountType(),specialChars, "account type", "bankAccount");
                containsPattern(((BankAccount) account).getLenderName(),specialChars, "lender", "bankAccount");
            }
        }
        if(map.containsKey("creditCardAccount")) {
            List<Account> creditCards = map.get("creditCardAccount");
            for (Account account : creditCards) {
                isNullOrEmpty(account.getAmount(),"creditCard");
                isNullOrEmpty(account.getAccountNumber(),"creditCard");
                isNullOrEmpty(account.getAccountType(),"creditCard");
                isNullOrEmpty(((CreditCard) account).getCreditCardNumber(),"creditCard");
                isNullOrEmpty(((CreditCard) account).getExpDate(),"creditCard");
                isNullOrEmpty(((CreditCard) account).getIssuer(),"creditCard");

                containsPattern(account.getAmount(),nonDigits, "amount", "creditCard");
                containsPattern(account.getAccountNumber(),nonDigits, "account number", "creditCard");
                containsPattern(account.getAccountType(),specialChars, "account type", "creditCard");
                containsPattern(((CreditCard) account).getCreditCardNumber(), specialChars, "credit card number", "creditCard");
                containsPattern(((CreditCard) account).getIssuer(),specialChars, "issuer", "creditCard");
                containsPattern(((CreditCard) account).getExpDate(),nonDigits, "expiry date", "creditCard");
            }
        }
        if(map.containsKey("mortgageAccount")) {
            List<Account> mortgages = map.get("mortgageAccount");
            for (Account account : mortgages) {
                isNullOrEmpty(account.getAmount(),"mortgageAccount");
                isNullOrEmpty(account.getAccountNumber(),"mortgageAccount");
                isNullOrEmpty(account.getAccountType(),"mortgageAccount");
                isNullOrEmpty(((Mortgage) account).getLender(),"mortgageAccount");

                containsPattern(account.getAmount(),nonDigits, "amount", "mortgageAccount");
                containsPattern(account.getAccountNumber(),nonDigits, "account number", "mortgageAccount");
                containsPattern(account.getAccountType(),specialChars, "account type", "mortgageAccount");
                containsPattern(((Mortgage) account).getLender(),specialChars, "lender", "mortgageAccount");
            }
        }
    }

    private void containsPattern(String input, String regex, String parameter, String accountType) throws Exception {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) throw new Exception("Invalid " + parameter +" for "+ accountType);
    }

    private void isNullOrEmpty(String str, String accountType) throws Exception {
        if (StringUtils.isBlank(str))
            throw new Exception("null and blank string encountered in, " + accountType);
    }

    public void writeToFile(Map<String, List<Account>> map) throws IOException {
        FileWriter filewriter  = new FileWriter("output1.txt",true);
        BufferedWriter writer = new BufferedWriter(filewriter);

        //retrieving all user accounts from map
        Collection<List<Account>> lists = map.values();

        for(List<Account> list: lists) {
            for(Account account: list) {
                Description description = account.createDescription();
                writer.write(description.toString() +", amount: "+ account.getAmount() +"\n\n");
            }
        }
        writer.flush();
        writer.close();
    }
}
