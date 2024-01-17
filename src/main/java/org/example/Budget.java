package org.example;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class Budget {
    private int budgetID;
    private User user;
    private CategoryExpense category;
    private double amount;
    private int month;
    private int year;

    // Constructors

    public Budget() {
        // Default constructor
    }

    public Budget(User user, CategoryExpense category, double amount, int month, int year) {
        this.user = user;
        this.category = category;
        this.amount = amount;
        this.month = month;
        this.year = year;
    }

    // Getters and setters

    public int getBudgetID() {
        return budgetID;
    }

    public void setBudgetID(int budgetID) {
        this.budgetID = budgetID;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public CategoryExpense getCategory() {
        return category;
    }

    public void setCategory(CategoryExpense category) {
        this.category = category;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
    public void ViewListOfBudgets(User user, Scanner scanner) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();


            List<Budget> budgetList = session.createQuery("FROM Budget WHERE user = :user", Budget.class)
                    .setParameter("user", user)
                    .getResultList();

            if (budgetList.isEmpty()) {
                System.out.print("No budgets found.");
            } else {
                System.out.println("Budget:");
                for (Budget budget: budgetList) {
                    System.out.println("Category: " + budget.getCategory().getCategoryName());
                    System.out.println("Amount:  " +  budget.getAmount());
                }
            }

            transaction.commit();
        } catch (HibernateException e) {

            e.printStackTrace();
        }
    }

    public void updateBudget(User myuser,Scanner scanner) {
        System.out.print("Enter the name of the category witch budget you want to update: ");
        String categoryName = scanner.nextLine();
        System.out.print("Enter the new amount: ");
        double newAmount = scanner.nextDouble();
        scanner.nextLine();
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction transaction = null;

            try {
                transaction = session.beginTransaction();
                Integer userID = myuser.getUserID();
                CategoryExpense category = session.createQuery("FROM CategoryExpense WHERE categoryName = :categoryName AND user = :user", CategoryExpense.class)
                        .setParameter("categoryName",categoryName)
                        .setParameter("user", myuser)
                        .uniqueResult();
                Budget budget = session.createQuery("FROM Budget WHERE category = :category AND user = :user", Budget.class)
                        .setParameter("category", category)
                        .setParameter("user", myuser)
                        .uniqueResult();
                if (budget != null && budget.getUser().getUserID()==(userID)) {
                    budget.setAmount(newAmount);
                    session.update(budget);
                    transaction.commit();
                }
            } catch (HibernateException e) {
                if (transaction != null) transaction.rollback();
                e.printStackTrace();
            } finally {
                session.close();
            }
            System.out.println("Budget's amount was updated successfully");
        } catch (org.hibernate.exception.ConstraintViolationException e) {
            System.out.println("Budget's amount wasn't updated successfully");
        }
    }
}




















