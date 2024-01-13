package org.example;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

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
                        // Add admin-specific logic here
                    } else {
                        System.out.println("This is a regular user.");
                        // Add regular user logic here
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
            user = new User(username, hashedPassword, UserType.USER.toString());
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
}

