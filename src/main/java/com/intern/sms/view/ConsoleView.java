// ConsoleView.java
package com.intern.sms.view;

import java.util.Scanner;

public class ConsoleView {
    private final Scanner scanner = new Scanner(System.in);

    public void showMenu(String title, String[] options) {
        System.out.println("\n==== " + title + " ====");
        for (int i = 0; i < options.length; i++) {
            System.out.println((i + 1) + ". " + options[i]);
        }
        System.out.print("Choose an option: ");
    }


    public void showLoggingOutMessage() {
        System.out.println("Logging out...");
    }

    public void showInvalidChoice() {
        System.out.println("Invalid choice.");
    }

    public void showUnderDevelopment(String featureName) {
        System.out.println(featureName + " functionality is under development.");
    }

    public int getChoice() {
        int choice = scanner.nextInt();
        scanner.nextLine(); // consume newline
        return choice;
    }

    public String prompt(String message) {
        System.out.print(message);
        return scanner.nextLine();
    }

    public void showMessage(String message) {
        System.out.println(message);
    }
}
