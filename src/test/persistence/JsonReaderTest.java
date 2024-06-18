package persistence;

import org.junit.jupiter.api.Test;
import model.*;
import java.io.IOException;
import java.util.List;
import java.util.Date;


import static org.junit.jupiter.api.Assertions.*;

// Citation: This class was modeled from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
public class JsonReaderTest extends JsonTest {

    @Test
    public void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/fileNotThere.json");
        try {
            MoodJournal journal = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    public void testReaderEmptyJournal() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyJournal.json");
        try {
            MoodJournal journal = reader.read();
            assertEquals("Empty Journal", journal.getName());
            assertEquals(0, journal.getEntries().size());
            assertTrue(journal.isMoodJournalEmpty());
        } catch (IOException e) {
            fail("Failed to read from file");
        }
    }

    @Test
    public void testReaderGeneralJournal() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralJournal.json");
        try {
            MoodJournal journal = reader.read();

            assertEquals("My Journal", journal.getName());

            List<JournalEntry> entries = journal.getEntries();
            assertEquals(2, entries.size());
            assertFalse(journal.isMoodJournalEmpty());

            checkEntryTest(new Date(1678089600000L), 3, 5, "sunny", entries.get(0));
            checkEntryTest(new Date(1678176000000L), 4, 6, "cloudy", entries.get(1));

        } catch (IOException e) {
            fail("Failed to read from file");
        }
    }

}