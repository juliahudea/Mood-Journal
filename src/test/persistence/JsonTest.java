package persistence;

import model.JournalEntry;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

// Citation: This class was modeled off of https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
public class JsonTest {

    // EFFECTS: Compares given entries with entered data
    protected void checkEntryTest(Date date, int mood, double sleep, String weather, JournalEntry entry) {
            assertEquals(date, entry.getDate());
            assertEquals(mood, entry.getMood());
            assertEquals(sleep, entry.getSleep());
            assertEquals(weather, entry.getWeather());
    }
}