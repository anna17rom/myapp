package org.example;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import java.text.ParseException;
import java.util.Scanner;

public class MainProgram {
   static User user;
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("1) Log in");
            System.out.println("2) Sign in");
            System.out.print("Choose an option: ");
            int option = scanner.nextInt();

            switch (option) {
                case 1:
                    login();

                    break;
                case 2:
                    signIn();
                    break;
                default:
                    System.out.println("Invalid option");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        HibernateUtil.shutdown();
    }

    private static void login() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter username: ");
            String username = scanner.nextLine();
            System.out.print("Enter password: ");
            String password = scanner.nextLine();

            try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                Transaction transaction = session.beginTransaction();

                // Check if the user exists
               user = (User) session.createQuery("FROM User WHERE username = ?1")
                        .setParameter(1, username)
                        .uniqueResult();

                if (user != null && user.verifyPassword(password)) {
                    System.out.println("Login successful");
                    if ("Admin".equals(user.getUserType())) {
                        System.out.println("This is an admin user.");
                       adminMenu();
                    } else {
                        System.out.println("This is a regular user.");
                       regularUserMenu();
                    }
                } else {
                    System.out.println("Invalid username or password");
                }

                transaction.commit();
            }
        }
    }

    private static void signIn() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter username: ");
            String username = scanner.nextLine();
            System.out.print("Enter password: ");
            String password = scanner.nextLine();
            try {
                addUser(username, hashPassword(password), UserType.USER.toString());
                System.out.println("User added successfully");
                regularUserMenu();
            } catch (org.hibernate.exception.ConstraintViolationException e) {
                // Catch the exception thrown by Hibernate for constraint violations
                System.out.println("Error: Username already exists");
            }
        }

    }


    private static void addUser(String username, String hashedPassword, String userType) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            User user = new User(username, hashedPassword, UserType.USER.toString());
            Wallet wallet = new Wallet(user,"Cash",0);
            CategoryExpense categoryExpense = new CategoryExpense("Groceries",user);
            Expense expense = new Expense(user,categoryExpense,0,"First Transaction",new Date(),wallet);
            Notification notification = new Notification(user, "Welcome",new Date(),0);
            Budget budget = new Budget(user,categoryExpense,0,new Date().getMonth()+1,new Date().getYear()+1900);


            // Set relationships
            user.getWallets().add(wallet);
            user.getExpenses().add(expense);
            user.getCategoryExpenses().add(categoryExpense);
            user.getNotifications().add(notification);
            user.getBudgets().add(budget);

            session.save(user);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

    }


    private static String hashPassword(String plainTextPassword) {
        // Assuming the User class already has a method to hash passwords
        user = new User("", "", null);
        user.hashPassword(plainTextPassword);
        return user.getPassword();
    }
    private static void regularUserMenu() {
        try (Scanner scanner = new Scanner(System.in)) {
            boolean run = true;
            int option;
            while (run) {
                System.out.println("Regular User Menu:");
                System.out.println("1) Add new expense");
                System.out.println("2) Set amount in wallets");
                System.out.println("3) Set budget");
                System.out.println("4) Add notification");
                System.out.println("5) Add category");
                System.out.println("6) View list of expenses");
                System.out.println("7) View budget");
                System.out.println("8) View list of wallets");
                System.out.println("9) View list of categories");
                System.out.println("10) View list of notifications");
                System.out.println("0) Logout");

                System.out.print("Choose an option: ");

                if (scanner.hasNextInt()) { // Check if the next input is an integer
                    option = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                } else {
                    System.out.println("Invalid option. Please enter a valid integer option.");
                    scanner.nextLine(); // Consume invalid input
                    continue;
                }

                switch (option) {
                    case 1:
                        Expense expense = new Expense();
                        expense.newExpense(user,scanner);
                        break;
                    case 2:
                        Wallet wallet = new Wallet();
                        wallet.updateAmount(user,scanner);
                        break;
                    case 3:
                        Budget budget =new Budget();
                        budget.updateBudget(user,scanner);
                        break;
                    case 4:
                        Notification notification = new Notification();
                        notification.newNotification(user,scanner);
                        break;
                    case 5:
                       CategoryExpense categoryExpense = new CategoryExpense();
                       categoryExpense.addNewCategory(user,scanner);
                        break;
                    case 6:
                       Expense expense1 = new Expense();
                       expense1.ViewListOfAllExpenses(user,scanner);
                        break;
                    case 7:
                        Budget budget1 = new Budget();
                        budget1.ViewListOfBudgets(user, scanner);
                        break;
                    case 8:
                        Wallet wallet1 = new Wallet();
                        wallet1.ViewListOfWallets(user,scanner);
                        break;
                    case 9:
                        CategoryExpense categoryExpense1 =new CategoryExpense();
                        categoryExpense1.ViewListOfCategories(user,scanner);
                        break;
                    case 10:
                        Notification notification1 = new Notification();
                        notification1.ViewListOfNotifications(user,scanner);
                        break;
                    case 0:
                        System.out.println("Logging out...");
                        run = false;
                        break;
                    default:
                        System.out.println("Invalid option");
                }
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }


    private static void adminMenu() {
        try (Scanner scanner = new Scanner(System.in)) {
            int option;
            do {
                System.out.println("Admin Menu:");
                System.out.println("1) View list of all users");
                System.out.println("2) Add new user");
                System.out.println("3) Delete user");
                System.out.println("0) Logout");

                System.out.print("Choose an option: ");
                option = scanner.nextInt();

                switch (option) {
                    case 1:
                        viewListOfAllUsers();
                        break;
                    case 2:
                        System.out.print("Enter the username: ");
                        scanner.nextLine();
                        String username = scanner.nextLine();
                        System.out.print("Enter the password: ");
                        String password = scanner.nextLine();
                        addUser(username,hashPassword(password),UserType.USER.toString());
                        break;
                    case 3:
                        System.out.print("Enter user's ID: ");
                        int userID = scanner.nextInt();
                        deleteEmployee(userID);
                        break;
                    case 0:
                        System.out.println("Logging out...");
                        break;
                    default:
                        System.out.println("Invalid option");
                }
            } while (option != 0);
        }


    }
    private static void viewListOfAllUsers() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            // Retrieve the list of all users
            List<User> userList = session.createQuery("FROM User", User.class).getResultList();

            if (userList.isEmpty()) {
                System.out.println("No users found.");
            } else {
                System.out.println("List of all users:");
                for (Iterator<User> iterator = userList.iterator(); iterator.hasNext();) {
                    User user = iterator.next();
                    System.out.println("UserID: " + user.getUserID());
                    System.out.println("Username: " + user.getUsername());
                    System.out.println("Password: "+user.getPassword());
                    System.out.println("UserType: " + user.getUserType());

                }
            }

            transaction.commit();
        } catch (HibernateException e) {

            e.printStackTrace();
        }
    }
   private static void deleteEmployee(Integer UserID){
        Session factory = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = factory.beginTransaction();
            User user = factory.get(User.class, UserID);
            factory.remove(user);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction!=null) transaction.rollback();
            e.printStackTrace();
        } finally {
            factory.close();
        }
    }
}

