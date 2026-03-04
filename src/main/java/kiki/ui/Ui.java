package kiki.ui;

import java.util.Scanner;

public class Ui {
    private Scanner scanner;
    private final String LOGO = " _  __ _ _    _\n"
            + "| |/ /(_) | _(_)\n"
            + "| ' / | | |/ / |\n"
            + "| . \\ | |   <| |\n"
            + "|_|\\_\\|_|_|\\_\\_|\n";

    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    public void showWelcome() {
        System.out.println("Hey what's up, from\n" + LOGO);
        System.out.println("How can I help you today?");
    }

    public String readCommand() {
        return scanner.nextLine().trim();
    }

    public void showLine() {
        System.out.println("____________________________________________________________");
    }

    public void showError(String msg) {
        System.out.println("Error: " + msg);
    }

    public void showLoadingError() {
        System.out.println("(Oops, problem loading saved tasks. Starting with a fresh list!)");
    }

    public void printMessage(String msg) {
        System.out.println(msg);
    }
}
