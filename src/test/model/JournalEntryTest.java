package model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Date;

class JournalEntryTest {
    private JournalEntry testEntry;
    private Date testDate;

    @BeforeEach
    public void runBefore() {
        testDate = new Date();
        testEntry = new JournalEntry(testDate, 4, 7.5, "sunny");
    }

    @Test
    public void testConstructor() {
        assertEquals(testDate, testEntry.getDate());
        assertEquals(4, testEntry.getMood());
        assertEquals(7.5, testEntry.getSleep());
        assertEquals("sunny", testEntry.getWeather());
    }

    @Test
    public void testSetDate() {
        Date newDate = new Date();

        testEntry.setDate(newDate);
        assertEquals(newDate, testEntry.getDate());
    }

    @Test
    public void testSetMood() {
        testEntry.setMood(2);
        assertEquals(2, testEntry.getMood());
    }

    @Test
    public void testSetSleep() {
        testEntry.setSleep(6);
        assertEquals(6, testEntry.getSleep());
    }

    @Test
    public void testSetWeather() {
        testEntry.setWeather("cloudy");
        assertEquals("cloudy", testEntry.getWeather());
    }
}