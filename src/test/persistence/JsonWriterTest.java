package persistence;

import model.*;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.List;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;

// Citation: This class was modeled from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            MoodJournal journal = new MoodJournal("My journal");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWorkroom() {
        try {
            MoodJournal journal = new MoodJournal("My journal");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyJournal.json");
            writer.open();
            writer.write(journal);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyJournal.json");
            journal = reader.read();
            assertEquals("My journal", journal.getName());
            assertEquals(0, journal.getEntries().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWorkroom() {
        try {
            MoodJournal journal = new MoodJournal("My journal");
            journal.addEntry(new JournalEntry(new Date(1678176000000L), 4, 6, "cloudy"));
            journal.addEntry(new JournalEntry(new Date(1678089600000L), 3, 5, "sunny"));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralJournal.json");
            writer.open();
            writer.write(journal);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralJournal.json");
            journal = reader.read();
            assertEquals("My journal", journal.getName());
            List<JournalEntry> entries = journal.getEntries();
            assertEquals(2, entries.size());
            checkEntryTest(new Date(1678176000000L), 4, 6, "cloudy", entries.get(0));
            checkEntryTest(new Date(1678089600000L), 3, 5, "sunny", entries.get(1));
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
