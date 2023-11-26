package com.sheffield.model.user;

public class BankDetails {
    private String cardNumber;
    private String bankName;
    private String holderName;
    private boolean validCard;
    private String cardExpDate;
    private String secCode;
    
    // Constructor to initialize a Details object with its attributes

    public BankDetails(String cardNumber, String bankName, String holderName, String cardExpDate, String secCode) {
        this.setcardNumber(cardNumber);
        this.setholderName(holderName);
        this.setbankName(bankName);
        this.setcardExpDate(cardExpDate);
        this.setsecCode(secCode);
        this.setvalidCard(isValidCard());
    }

    // Getter and setter methods for the cardNumber attribute with validation
    public String getcardNumber() {
        return cardNumber;
    }

    public void setcardNumber(String cardNumber) {
        if (isValidcardNumber(cardNumber)) {
            this.cardNumber = cardNumber;
        } else {
            throw new IllegalArgumentException("cardNumber is not valid.");
        }
    }

    // Getter and setter methods for the holderName attribute with validation
    public String getholderName() {
        return holderName;
    }

    public void setholderName(String holderName) {
        if (isValidholderName(holderName)) {
            this.holderName = holderName;
        } else {
            throw new IllegalArgumentException("holderName is not valid.");
        }
    }

    // Getter and setter methods for the Genre attribute with validation
    public String getbankName() {
        return bankName;
    }

    public void setbankName(String bankName) {
        this.bankName = bankName;
    }

    public boolean getvalidCard() {
        return validCard;
    }    

    public void setvalidCard(boolean validCard) {
        this.validCard = validCard;
    }

    public void setcardExpDate(String cardExpDate) {
        this.cardExpDate = cardExpDate;
    }

    public String getcardExpDate() {
        return cardExpDate;
    }

    public void setsecCode(String secCode) {
        if (isValidsecCode(secCode)) {
            this.secCode = secCode;
        } else {
            throw new IllegalArgumentException("secCode is not valid.");
        }
    }

    public String getsecCode() {
        return secCode;
    }


    // Private validation methods for each attribute

    private boolean isValidcardNumber(String cardNumber) {
        // Implement cardNumber validation logic here (e.g., length, format)
        return cardNumber != null && cardNumber.length() == 16;
    }

    private boolean isValidholderName(String holderName) {
        // Implement holderName validation logic here (e.g., length)
        return holderName != null && holderName.length() <= 100;
    }

    private boolean isValidsecCode(String secCode) {
        // Implement secCode validation logic here (e.g., length, format)
        return secCode != null && secCode.length() == 3;
    }

    private boolean isValidCard() {
        // Implement overall card validation logic here (e.g., length, format)
        return isValidcardNumber(this.cardNumber) && isValidholderName(this.holderName) && isValidsecCode(this.secCode);
    }


    @Override
    public String toString() {
        return "{ " +
            " cardNumber='" + getcardNumber() + "'" +
            ", holderName='" + getholderName() + "'" +
            ", Valid card ='" + getvalidCard() + "'" +
            ", cardExpDate ='" + getcardExpDate() + "'" +
            ", secCode ='" + getsecCode() + "'" +
            " }";
    }

}
