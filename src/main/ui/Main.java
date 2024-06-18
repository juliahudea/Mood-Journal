package ui;

import java.io.FileNotFoundException;

// Represents the main program execution class
public class Main {

    // Method for running the console interface
    public static void main(String[] args) {
        try {
            new JournalApp();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to run application: file not found");
        }
    }
}
