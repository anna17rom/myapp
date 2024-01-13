package org.example;public class Wallet {
    private int walletID;
    private int userID;
    private String walletName;
    private double amount;

    public Wallet(int userID, String walletName, double amount) {
        this.userID = userID;
        this.walletName = walletName;
        this.amount = amount;
    }


    public int getWalletID() {
        return walletID;
    }

    public void setWalletID(int walletID) {
        this.walletID = walletID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getWalletName() {
        return walletName;
    }

    public void setWalletName(String walletName) {
        this.walletName = walletName;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
