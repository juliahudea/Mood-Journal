package ui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.swing.*;
import java.awt.*;

// Popup window for adding a new journal entry
public class AddNewEntryWindow {
    JPanel panel;
    JFrame frame;
    JTextField date;
    JLabel dateLabel;
    JTextField mood;
    JLabel moodLabel;
    JTextField sleep;
    JLabel sleepLabel;
    JTextField weather;
    JLabel weatherLabel;

    // EFFECTS: creates a window for adding a new journal entry
    public AddNewEntryWindow() {
        createEntryFields();

        panel.add(dateLabel);
        panel.add(date);
        panel.add(moodLabel);
        panel.add(mood);
        panel.add(sleepLabel);
        panel.add(sleep);
        panel.add(weatherLabel);
        panel.add(weather);

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setPreferredSize(new Dimension(300,250));
    }

    // EFFECTS: creates the fields for the journal entry
    private void createEntryFields() {
        frame = new JFrame("New Journal Entry");
        panel = new JPanel();

        dateLabel = new JLabel("Enter date (yyyy-mm-dd):");
        moodLabel = new JLabel("Enter mood (1-5):");
        sleepLabel = new JLabel("Enter hours of sleep:");
        weatherLabel = new JLabel("Enter weather:");

        date = new JTextField();
        date.setPreferredSize(new Dimension(300, 25));
        mood = new JTextField();
        mood.setPreferredSize(new Dimension(300, 25));
        sleep = new JTextField();
        sleep.setPreferredSize(new Dimension(300, 25));
        weather = new JTextField();
        weather.setPreferredSize(new Dimension(300, 25));
    }

    public JPanel returnJPanel() {
        return panel;
    }

    // EFFECTS: converts date text to Date object and returns Date
    public Date getDate() {
        String dateString = date.getText();
        Date date = null;

        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
        } catch (ParseException e) {
            System.out.println("Invalid date format. Please try again.");
        }

        return date;
    }

    // EFFECTS: parses mood text and returns an integer
    public int getMood() {
        return Integer.parseInt(mood.getText());
    }

    // EFFECTS: parses sleep text and returns an integer
    public double getSleep() {
        return Double.parseDouble(sleep.getText());
    }

    // EFFECTS: retrieves the weather info entered
    public String getWeather() {
        return weather.getText();
    }

}
