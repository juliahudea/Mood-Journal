package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

/**
 * MoodJournal is a collection of JournalEntry objects with a name of the journal. The class provides
 * methods for adding entries, retrieving entries, checking if empty.
 * Multiple entries with the same date are not allowed.
 */
public class MoodJournal implements Writable {
    private String name;
    private List<JournalEntry> journal;

    public MoodJournal(String name) {
        this.name = name;
        this.journal = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    // MODIFIES: this
    // EFFECTS: if an entry does not exist in journal for that date
    //          it adds entry to journal, otherwise does nothing
    public Boolean addEntry(JournalEntry entry) {
        if (!isMoodJournalEmpty()) {
            Date date = entry.getDate();
            if (getEntryByDate(date) == null) {
                this.journal.add(entry);
                EventLog.getInstance().logEvent(new Event("Added entry."));
                return true;
            } else {
                return false;
            }
        } else {
            this.journal.add(entry);
            EventLog.getInstance().logEvent(new Event("Added entry."));
            return true;
        }
    }

    // REQUIRES: non-empty list, 0 <= index < journal.size()
    // EFFECTS: returns the journal entry at given index
    public JournalEntry getEntryByIndex(int index) {
        EventLog.getInstance().logEvent(new Event("Retrieved entry."));
        return this.journal.get(index);
    }

    // REQUIRES: non-empty mood journal
    // EFFECTS: if journal entry with given date exists, returns entry
    //          otherwise returns null
    public JournalEntry getEntryByDate(Date date) {
        for (JournalEntry entry : this.journal) {
            if (entry.getDate().equals(date)) {
                EventLog.getInstance().logEvent(new Event("Retrieved entry."));
                return entry;
            }
        }
        return null;
    }


    // EFFECTS: returns true if mood journal is empty, false otherwise
    public Boolean isMoodJournalEmpty() {
        return this.journal.isEmpty();
    }

    public List<JournalEntry> getEntries() {
        EventLog.getInstance().logEvent(new Event("Retrieved entries."));
        return this.journal;
    }

    // CITATION: modeled from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    // EFFECTS: converts mood journal to JSON object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", this.name);
        json.put("journal entries", entriesToJson(journal));
        return json;
    }

    // Citation: this method was modeled from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // EFFECTS: returns entries in this mood journal as a JSON array
    public JSONArray entriesToJson(List<JournalEntry> journal) {
        JSONArray jsonArray = new JSONArray();

        for (JournalEntry entry : journal) {
            jsonArray.put(entry.toJson());
        }
        return jsonArray;
    }

    // EFFECTS: prints all logged events
    public void quitApplication() {
        for (Event event : EventLog.getInstance()) {
            System.out.println(event.toString());
        }
    }
}
