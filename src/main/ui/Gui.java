package ui;

import model.JournalEntry;
import model.MoodJournal;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.List;

// This class is a graphical user interface
public class Gui extends JFrame {
    private MoodJournal journal;
    private int moodFilter;
    private JTextField filterValue;

    private static final String JSON_STORE = "./data/moodJournal.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    private JList<JournalEntry> journalEntryJList;
    private DefaultListModel<JournalEntry> journalEntryListModel;
    private ImageIcon journalImage;

    // EFFECTS: Creates and sets up the main application window
    public Gui() {
        super("Mood Tracker App");
        setLayout(new FlowLayout());
        setPreferredSize(new Dimension(700, 500));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        jsonReader = new JsonReader(JSON_STORE);
        jsonWriter = new JsonWriter(JSON_STORE);
        journal = new MoodJournal("Your Mood Journal");
        journalEntryListModel = new DefaultListModel<>();
        journalImage = new ImageIcon("./data/journal.jpg");

        setUpHeader();
        setUpJournalPane();
        setUpButtons();
        pack();
        setVisible(true);

        loadJournal();
        saveJournal();
    }

    // MODIFIES: this
    // EFFECTS: creates the header panel
    private void setUpHeader() {
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new FlowLayout());

        JLabel title = new JLabel(journal.getName());
        JPanel titlePane = new JPanel();
        titlePane.add(title);
        titlePane.setPreferredSize(new Dimension(400, 30));
        titlePane.setBorder(BorderFactory.createLineBorder(Color.black));

        headerPanel.add(titlePane);

        add(headerPanel, BorderLayout.PAGE_START);
    }

    // MODIFIES: this
    // EFFECTS: creates panel to display journal entries
    private void setUpJournalPane() {
        journalEntryJList = new JList<>(journalEntryListModel);
        journalEntryJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        journalEntryJList.setSelectedIndex(0);

        JScrollPane journalPane = new JScrollPane(journalEntryJList);
        journalPane.createVerticalScrollBar();
        journalPane.setHorizontalScrollBar(null);

        journalPane.setPreferredSize(new Dimension(700, 300));
        journalPane.setBorder(BorderFactory.createLineBorder(Color.black));

        add(journalPane, BorderLayout.CENTER);
    }

    // MODIFIES: this
    // EFFECTS: sets up buttons along a bottom panel
    private void setUpButtons() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        JButton newEntry = new JButton("Add Entry");
        newEntry.setActionCommand("Add");
        newEntry.addActionListener(new ButtonActionListener());

        JButton viewEntry = new JButton("View Entry");
        viewEntry.setActionCommand("View");
        viewEntry.addActionListener(new ButtonActionListener());

        JButton filter = new JButton("Filter Entries");
        filter.setActionCommand("Filter");
        filter.addActionListener(new ButtonActionListener());

        buttonPanel.add(newEntry);
        buttonPanel.add(viewEntry);
        JLabel filterLabel = new JLabel("Filter entries by mood (leave blank to remove filter):");

        filterValue = new JTextField();
        filterValue.setPreferredSize(new Dimension(40, 25));

        buttonPanel.add(filterLabel);
        buttonPanel.add(filterValue);
        buttonPanel.add(filter);

        add(buttonPanel, BorderLayout.PAGE_END);
    }

    // MODIFIES: this
    // EFFECTS: creates a popup window to prompt file loading at start
    private void loadJournal() {
        int load = JOptionPane.showConfirmDialog(null,
                "Would you like to load a journal?",
                "Load journal", JOptionPane.YES_NO_OPTION);
        if (load == JOptionPane.YES_OPTION) {
            try {
                journal = jsonReader.read();
                updateJournal();
            } catch (IOException e) {
                System.out.println("Unable to read from file: " + JSON_STORE);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: creates a popup window to prompt file saving at exit
    private void saveJournal() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent event) {
                int save = JOptionPane.showConfirmDialog(null,
                        "Would you like to save journal before exiting?",
                        "Save File",
                        JOptionPane.YES_NO_OPTION);
                if (save == JOptionPane.YES_OPTION) {
                    try {
                        jsonWriter.open();
                        jsonWriter.write(journal);
                        jsonWriter.close();
                    } catch (FileNotFoundException e) {
                        System.out.println("Unable to write to file: " + JSON_STORE);
                    }
                }
                journal.quitApplication();
                dispose();
            }
        });
    }

    // An action listener for button events
    class ButtonActionListener implements ActionListener {
        // EFFECTS: processes button clicks and runs appropriate methods
        @Override
        public void actionPerformed(ActionEvent e) {
            switch (e.getActionCommand()) {
                case "Add":
                    addEntryAction();
                    break;
                case "View":
                    viewEntryAction();
                    break;
                case "Filter":
                    try {
                        moodFilter = Integer.parseInt(filterValue.getText());
                        updateFilteredJournal();
                    } catch (NumberFormatException nfe) {
                        updateJournal();
                    }
                    break;
            }
        }

        // MODIFIES: this, journal
        // EFFECTS: creates a popup window for adding a new journal entry,
        // adds entry to journal and updates the screen
        private void addEntryAction() {

            AddNewEntryWindow addEntryWindow = new AddNewEntryWindow();
            JPanel panel = addEntryWindow.returnJPanel();

            int optionPaneValue = JOptionPane.showConfirmDialog(null, panel,
                    "Add New Journal Entry", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE,
                    journalImage);

            if (optionPaneValue == JOptionPane.OK_OPTION) {
                Date date = addEntryWindow.getDate();
                int mood = addEntryWindow.getMood();
                double sleep = addEntryWindow.getSleep();
                String weather = addEntryWindow.getWeather();

                JournalEntry entry = new JournalEntry(date, mood, sleep, weather);

                journal.addEntry(entry);
                updateJournal();
            }
        }

        // EFFECTS: displays details of journal entry in popup window
        private void viewEntryAction() {
            int index = journalEntryJList.getSelectedIndex();
            JournalEntry entry = journal.getEntryByIndex(index);

            ViewEntryWindow viewEntryWindow = new ViewEntryWindow(entry);

            JPanel panel = viewEntryWindow.returnJPanel();

            JOptionPane.showMessageDialog(null, panel,
                    "Add New Journal Entry", JOptionPane.PLAIN_MESSAGE);
        }

        // MODIFIES: this
        // EFFECTS: if the filter value is equal to 0 removes filter from entries
        // otherwise it updates the displayed entries to show only those with specified mood
        private void updateFilteredJournal() {
            if (moodFilter == 0) {
                updateJournal();
            } else {
                journalEntryListModel.clear();
                List<JournalEntry> journalEntries = journal.getEntries();
                for (JournalEntry entry : journalEntries) {
                    if (entry.getMood() == moodFilter) {
                        journalEntryListModel.addElement(entry);
                    }
                }
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: updates the entries in the journal that are shown on screen
    private void updateJournal() {
        journalEntryListModel.clear();
        List<JournalEntry> journalEntries = journal.getEntries();
        for (JournalEntry entry : journalEntries) {
            journalEntryListModel.addElement(entry);
        }
    }
}


