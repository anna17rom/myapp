package org.example;
import java.util.Date;

public class TransactionA {
    private int transactionID;
    private int userID;
    private int categoryID;
    private double amount;
    private String description;
    private Date transactionDate;
    private int walletType;
    private TransactionType transactionType;



    public enum TransactionType {
        INCOME,
        EXPENSE
    }


    public TransactionA(int transactionID, int userID, int categoryID, double amount, String description,
                       Date transactionDate,int walletType, TransactionType transactionType) {
        this.transactionID = transactionID;
        this.userID = userID;
        this.categoryID = categoryID;
        this.amount = amount;
        this.description = description;
        this.transactionDate = transactionDate;
        this.walletType = walletType;
        this.transactionType = transactionType;
    }

    // Getters and setters

    public int getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(int transactionID) {
        this.transactionID = transactionID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public int getWalletType() {
        return walletType;
    }

    public void setWalletType(int walletType) {
        this.walletType = walletType;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }
}

