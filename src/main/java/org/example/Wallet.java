package org.example;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;


import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Wallet {
    private int walletID;
    private User user;
    private String walletName;
    private double amount;
    private Set<Expense> expenses;

    public Wallet(User user, String walletName, double amount) {
        this.user = user;
        this.walletName = walletName;
        this.amount = amount;
    }

public Wallet(){}
    public int getWalletID() {
        return walletID;
    }

    public void setWalletID(int walletID) {
        this.walletID = walletID;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
    public Set<Expense> getExpenses() {
        return expenses;
    }

    public void setExpenses(Set<Expense> expenses) {
        this.expenses = expenses;
    }

    public void updateAmount(User myuser,Scanner scanner) {
        System.out.print("Enter the name of the wallet to update: ");
        String walletName = scanner.nextLine();
        System.out.print("Enter the new amount: ");
        double newAmount = scanner.nextDouble();
        scanner.nextLine();
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction transaction = null;

            try {
                transaction = session.beginTransaction();
                Integer userID = myuser.getUserID();
                User user = session.load(User.class, userID);
                Wallet wallet = session.createQuery("FROM Wallet WHERE walletName = :walletName AND user = :user", Wallet.class)
                        .setParameter("walletName", walletName)
                        .setParameter("user", myuser)
                        .uniqueResult();
                if (wallet != null && wallet.getUser().getUserID()==(userID)) {
                    wallet.setAmount(newAmount+wallet.getAmount());
                    session.update(wallet);
                    transaction.commit();
                }
            } catch (HibernateException e) {
                if (transaction != null) transaction.rollback();
                e.printStackTrace();
            } finally {
                session.close();
            }
            System.out.println("Wallet's amount was updated successfully");
        } catch (org.hibernate.exception.ConstraintViolationException e) {
            System.out.println("Wallet's amount wasn't updated successfully");
        }
    }

    public void ViewListOfWallets(User user, Scanner scanner) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();


            List<Wallet> walletList = session.createQuery("FROM Wallet WHERE user = :user", Wallet.class)
                    .setParameter("user", user)
                    .getResultList();

            if (walletList.isEmpty()) {
                System.out.print("No expenses found.");
            } else {
                System.out.println("List of all expenses:");
                for (Wallet wallet : walletList) {
                    System.out.println("WalletName: " + wallet.getWalletName());
                    System.out.println("Amount:  " + wallet.getAmount());
                }
            }

            transaction.commit();
        } catch (HibernateException e) {

            e.printStackTrace();
        }
    }
}
