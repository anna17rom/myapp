package org.example;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Notification {
    private int notificationID;
    private User user;
    private String textOfNotification;
    private Date date;
    private double amount;

    // Constructors

    public Notification() {
        // Default constructor
    }

    public Notification(User user, String textOfNotification, Date date, double amount) {
        this.user = user;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public void newNotification(User myuser, Scanner scanner) throws ParseException {
        System.out.print("Enter the text of notification: ");
        String textOfNotification = scanner.nextLine();
        System.out.print("Date (yyyy-MM-dd): ");
        String dateString = scanner.nextLine();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = dateFormat.parse(dateString);
        System.out.print("Enter the amount of future expense ");
        double amount = scanner.nextDouble();

        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction transaction = null;

            try {
                transaction = session.beginTransaction();
                Integer userID = myuser.getUserID();
                User user = session.load(User.class, userID);
                Notification notification = new Notification(user,textOfNotification,date,amount);
                session.save(notification);
                transaction.commit();
            } catch (HibernateException e) {
                if (transaction != null) transaction.rollback();
                e.printStackTrace();
            } finally {
                session.close();
            }
            System.out.println("Notification was added successfully");
        } catch (org.hibernate.exception.ConstraintViolationException e) {
            System.out.println("Error: Notification wasn't added");
        }
    }
    public void ViewListOfNotifications(User user, Scanner scanner) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();


            List<Notification> notificationList = session.createQuery("FROM Notification WHERE user = :user", Notification.class)
                    .setParameter("user", user)
                    .getResultList();

            if (notificationList.isEmpty()) {
                System.out.print("No categories found.");
            } else {
                System.out.println("List of all categories:");
                for (Notification notification: notificationList) {
                    System.out.println("Notification: " + notification.getTextOfNotification());
                    System.out.println("Date when you need to pay: " + notification.getDate());
                    System.out.println("Amount to pay: " + notification.getAmount());


                }
            }

            transaction.commit();
        } catch (HibernateException e) {

            e.printStackTrace();
        }
    }
}

