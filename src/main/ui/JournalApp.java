package ui;

import model.*;
import persistence.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.Scanner;
import java.text.SimpleDateFormat;
import java.text.ParseException;

// Represents the user interface console
public class JournalApp {
    private MoodJournal journal;
    private Scanner input;

    private static final String JSON_STORE = "./data/moodJournal.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    public JournalApp() throws FileNotFoundException {
        journal = new MoodJournal("Julia's Journal");
        input = new Scanner(System.in);
        input.useDelimiter("\n");

        jsonReader = new JsonReader(JSON_STORE);
        jsonWriter = new JsonWriter(JSON_STORE);

        runJournalApp();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    public  void runJournalApp() {
        boolean keepGoing = true;
        String command;

        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                journal.quitApplication();
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("\nGoodbye!");
    }

    private void processCommand(String command) {
        if (command.equals("a")) {
            addJournalEntry();
        } else if (command.equals("f")) {
            lookForJournalEntry();
        } else if (command.equals("p")) {
            printAllJournalEntries();
        } else if (command.equals("s")) {
            saveMoodJournal();
        } else if (command.equals("l")) {
            loadMoodJournal();
        } else {
            System.out.println("\nSelection not valid.");
        }
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\ta -> add a journal entry");
        System.out.println("\tf -> find journal entry by date");
        System.out.println("\tp -> print all journal entries");
        System.out.println("\ts -> save mood journal to file");
        System.out.println("\tl -> load mood journal from file");
        System.out.println("\tq -> quit");
    }

    // MODIFIES: this
    // EFFECTS: adds a new journal entry to the journal
    private void addJournalEntry() {
        Date date;
        int mood;
        double sleep;
        String weather;

        System.out.println("\nEnter date (yyyy-mm-dd): ");
        date = getDateInput();

        System.out.println("\nEnter mood (1-5): ");
        mood = input.nextInt();

        System.out.println("\nEnter hours of sleep: ");
        sleep = input.nextDouble();

        System.out.println("\nEnter weather: ");
        weather = input.next();

        JournalEntry newEntry = new JournalEntry(date, mood, sleep, weather);
        boolean success = journal.addEntry(newEntry);

        if (success) {
            System.out.println("\nJournal entry added!");
        } else {
            System.out.println("\nJournal entry already exists for this date.");
        }
    }

    // EFFECTS: searches for journal entry with corresponding date and displays it
    private void lookForJournalEntry() {
        System.out.println("\nEnter date (yyyy-mm-dd): ");
        Date date = getDateInput();

        JournalEntry entry = journal.getEntryByDate(date);

        if (entry != null) {
            System.out.println("\nJournal entry found: ");

            SimpleDateFormat formatter = new SimpleDateFormat("MMM dd yyyy");
            String formattedDate = formatter.format(entry.getDate());
            System.out.println(formattedDate);

            System.out.println("Mood: " + entry.getMood());
            System.out.println("Sleep: " + entry.getSleep());
            System.out.println("Weather: " + entry.getWeather());
        } else {
            System.out.println("\nJournal entry does not exist for this date.");
        }
    }

    // EFFECTS: displays all journal entries in the journal
    private void printAllJournalEntries() {
        if (journal.isMoodJournalEmpty()) {
            System.out.println("\nNo journal entries found.");
        } else {
            System.out.println("\nAll entries in " + journal.getName() + " :");
            for (JournalEntry entry : journal.getEntries()) {
                System.out.println(); // prints an empty line

                SimpleDateFormat formatter = new SimpleDateFormat("MMM dd yyyy");
                String formattedDate = formatter.format(entry.getDate());
                System.out.println(formattedDate);

                System.out.println("Mood: " + entry.getMood());
                System.out.println("Sleep: " + entry.getSleep());
                System.out.println("Weather: " + entry.getWeather());
            }
        }
    }

    // EFFECTS: prompts user to enter a date in yyyy-mm-dd format and returns a Date object
    private Date getDateInput() {
        String dateString = input.next().trim(); // consume entire line and remove leading/trailing whitespace
        Date date = null;

        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
        } catch (ParseException e) {
            System.out.println("Invalid date format. Please try again.");
        }

        return date;
    }

    // Citation: This method was modeled based on https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // EFFECTS: saves journal to file
    private void saveMoodJournal() {
        try {
            jsonWriter.open();
            jsonWriter.write(journal);
            jsonWriter.close();
            System.out.println("Saved " + journal.getName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // Citation: This method was modeled based on https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // MODIFIES: this
    // EFFECTS: loads journal from file
    private void loadMoodJournal() {
        try {
            journal = jsonReader.read();
            System.out.println("Loaded " + journal.getName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

}
