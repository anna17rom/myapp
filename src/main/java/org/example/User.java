package org.example;
import org.mindrot.jbcrypt.BCrypt;
import java.util.Base64;
import java.util.HashSet;
import java.util.Set;

public class User {
    private int userID;
    private String username;
    private String password;
    private String userType;
    private Set<Wallet> wallets;
    private Set<CategoryExpense> categoryExpenses;
    private Set<Expense> expenses;
    private Set<Notification> notifications;
    private Set<Budget> budgets;


    public User(String username, String password, String userType) {
        this.username = username;
        this.password = password;
        this.userType = userType;
    }
    public User() {
    }

    // Getters and setters

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
    public Set<Wallet> getWallets() {
        if (wallets == null) {
            wallets = new HashSet<>();
        }
        return wallets;
    }

    public void setWallets(Set<Wallet> wallets) {
        this.wallets = wallets;
    }

    public Set<CategoryExpense> getCategoryExpenses() {

        if (categoryExpenses == null) {
            categoryExpenses = new HashSet<>();
        }return categoryExpenses;
    }

    public void setCategoryExpenses(Set<CategoryExpense> categoryExpenses) {
        this.categoryExpenses = categoryExpenses;
    }
    public Set<Notification> getNotifications() {

        if (notifications == null) {
            notifications = new HashSet<>();
        }return notifications;
    }

    public void setNotifications(Set<Notification> notifications) {
        this.notifications = notifications;
    }


    public Set<Expense> getExpenses() {
        if (expenses == null) {
            expenses = new HashSet<>();
        }
        return expenses;
    }

    public void setExpenses(Set<Expense> expenses) {
        this.expenses = expenses;
    }
    public Set<Budget> getBudgets() {
        if (budgets == null) {
            budgets = new HashSet<>();
        }return budgets;
    }
    public void setBudgets(Set<Budget> budgets ) {
        this.budgets = budgets;
    }

    public void hashPassword(String plainTextPassword) {
        String hashedPassword = BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
        this.password = hashedPassword;
    }

    // Verify if the provided password matches the stored hash
    public boolean verifyPassword(String plainTextPassword) {
        return BCrypt.checkpw(plainTextPassword, this.password);
    }

}

