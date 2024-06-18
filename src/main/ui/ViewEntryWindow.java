package ui;

import model.JournalEntry;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

// Popup window for viewing the details of a journal entry
public class ViewEntryWindow {
    JPanel panel;
    JFrame frame;
    JLabel dateLabel;
    JLabel moodLabel;
    JLabel sleepLabel;
    JLabel weatherLabel;

    // EFFECTS: creates a window to view the details of a journal entry
    public ViewEntryWindow(JournalEntry entry) {
        getEntryFields(entry);

        panel.add(dateLabel);
        panel.add(moodLabel);
        panel.add(sleepLabel);
        panel.add(weatherLabel);

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setPreferredSize(new Dimension(300,250));
    }

    // EFFECTS: creates the journal entry fields as JLabels
    private void getEntryFields(JournalEntry entry) {
        frame = new JFrame("Journal Entry");
        panel = new JPanel();

        SimpleDateFormat formatter = new SimpleDateFormat("MMM dd yyyy");
        String formattedDate = formatter.format(entry.getDate());
        int mood = entry.getMood();
        double sleep = entry.getSleep();
        String weather = entry.getWeather();

        dateLabel = new JLabel("Date: " + formattedDate);
        moodLabel = new JLabel("Mood (1-5): " + mood);
        sleepLabel = new JLabel("Hours of sleep: " + sleep);
        weatherLabel = new JLabel("Weather: " + weather);
    }

    public JPanel returnJPanel() {
        return panel;
    }
}
