package org.example;
import java.util.Date;

public class Notification {
    private int notificationID;
    private int userID;
    private String textOfNotification;
    private Date date;
    private double amount;

    // Constructors

    public Notification() {
        // Default constructor
    }

    public Notification(int notificationID, int userID, String textOfNotification, Date date, double amount) {
        this.notificationID = notificationID;
        this.userID = userID;
        this.textOfNotification = textOfNotification;
        this.date = date;
        this.amount = amount;
    }

    // Getters and setters

    public int getNotificationID() {
        return notificationID;
    }

    public void setNotificationID(int notificationID) {
        this.notificationID = notificationID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getTextOfNotification() {
        return textOfNotification;
    }

    public void setTextOfNotification(String textOfNotification) {
        this.textOfNotification = textOfNotification;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}

