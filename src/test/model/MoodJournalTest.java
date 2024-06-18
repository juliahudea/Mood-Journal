package model;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;

public class MoodJournalTest {
    private MoodJournal testJournal;
    private Date testDate;
    private Date newTestDate;
    private JournalEntry testEntry;

    @BeforeEach
    void runBefore() {
        testJournal = new MoodJournal("My journal");

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            testDate = format.parse("2023-02-24");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        testEntry = new JournalEntry(testDate, 5, 8.5, "sunny");
    }

    @Test
    void testNameOfJournal() {
        assertEquals("My journal", testJournal.getName());
    }

    @Test
    void testAddEntryToEmptyJournal() {
        List<JournalEntry> entries = testJournal.getEntries();

        assertTrue(testJournal.addEntry(testEntry));
        assertEquals(1, entries.size());
        assertEquals(testEntry, entries.get(0));
    }

    @Test
    void testAddEntryWhenDateDoesNotExist() {
        testJournal.addEntry(testEntry);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            newTestDate = format.parse("2023-02-25");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        JournalEntry newTestEntry = new JournalEntry(newTestDate, 4, 7, "cloudy");

        assertTrue(testJournal.addEntry(newTestEntry));
        List<JournalEntry> entries = testJournal.getEntries();
        assertEquals(2, entries.size());
        assertEquals(newTestEntry, entries.get(1));
    }

    @Test
    void testAddEntryWhenDateExists() {
        testJournal.addEntry(testEntry);
        JournalEntry newTestEntry = new JournalEntry(testDate, 4, 7, "cloudy");

        assertFalse(testJournal.addEntry(newTestEntry));
        List<JournalEntry> entries = testJournal.getEntries();
        assertEquals(1, entries.size());
        assertEquals(testEntry, entries.get(0));
    }

    @Test
    void testGetEntryByIndex() {
        testJournal.addEntry(testEntry);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            newTestDate = format.parse("2023-02-25");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        JournalEntry newTestEntry = new JournalEntry(newTestDate, 4, 7, "cloudy");
        testJournal.addEntry(newTestEntry);

        assertEquals(testEntry, testJournal.getEntryByIndex(0));
        assertEquals(newTestEntry, testJournal.getEntryByIndex(1));
    }

    @Test
    void testGetEntryByDate() {
        testJournal.addEntry(testEntry);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            newTestDate = format.parse("2023-02-25");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        JournalEntry newTestEntry = new JournalEntry(newTestDate, 4, 7, "cloudy");
        testJournal.addEntry(newTestEntry);

        assertEquals(testEntry, testJournal.getEntryByDate(testDate));
        assertEquals(newTestEntry, testJournal.getEntryByDate(newTestDate));
    }

    @Test
    void testGetEntryByDateForEmptyJournal() {
        assertNull(testJournal.getEntryByDate(testDate));
    }

    @Test
    void testGetEntryByDateWhenEntryDoesNotExist() {
        testJournal.addEntry(testEntry);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            newTestDate = format.parse("2023-02-25");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        assertNull(testJournal.getEntryByDate(newTestDate));
    }

    @Test
    void testIsMoodJournalEmptyWhenEmpty() {
        assertTrue(testJournal.isMoodJournalEmpty());
    }

    @Test
    void testIsMoodJournalEmptyWhenNotEmpty() {
        testJournal.addEntry(testEntry);
        assertFalse(testJournal.isMoodJournalEmpty());
    }

    @Test
    void testToJson() {
        testJournal.addEntry(testEntry);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            newTestDate = format.parse("2023-02-25");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        JournalEntry newTestEntry = new JournalEntry(newTestDate, 4, 7, "cloudy");
        testJournal.addEntry(newTestEntry);
        JSONObject jsonObject = testJournal.toJson();
        JSONArray jsonArray = jsonObject.getJSONArray("journal entries");
        JSONObject jsonTestEntryOne = jsonArray.getJSONObject(0);
        JSONObject jsonTestEntryTwo = jsonArray.getJSONObject(1);

        assertEquals(new Date(jsonTestEntryOne.getLong("date")), testEntry.getDate());
        assertEquals(jsonTestEntryOne.getInt("mood"), testEntry.getMood());
        assertEquals(jsonTestEntryOne.getDouble("sleep"), testEntry.getSleep());
        assertEquals(jsonTestEntryOne.getString("weather"), testEntry.getWeather());

        assertEquals(new Date(jsonTestEntryTwo.getLong("date")), newTestEntry.getDate());
        assertEquals(jsonTestEntryTwo.getInt("mood"), newTestEntry.getMood());
        assertEquals(jsonTestEntryTwo.getDouble("sleep"), newTestEntry.getSleep());
        assertEquals(jsonTestEntryTwo.getString("weather"), newTestEntry.getWeather());
    }
}
