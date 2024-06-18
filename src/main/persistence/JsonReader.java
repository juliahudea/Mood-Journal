package persistence;

import model.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.stream.Stream;

import org.json.*;

// Citation: The class has been modeled based on https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
// Represents a reader that reads saved journal entries from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs a reader to read data from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads journal entry from file and returns it
    // throws IOException if an error occurs reading data from file
    public MoodJournal read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseMoodJournal(jsonObject);
    }

    // EFFECTS: reads source file as a string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }
        return contentBuilder.toString();
    }

    // parses log from JSON object and returns in
    private MoodJournal parseMoodJournal(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        MoodJournal mj = new MoodJournal(name);
        addEntries(mj, jsonObject);
        return mj;
    }

    // MODIFIES: mj
    // EFFECTS: parses entries from JSON object and adds them to mood journal
    private void addEntries(MoodJournal mj, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("journal entries");
        for (Object json : jsonArray) {
            JSONObject nextEntry = (JSONObject) json;
            addEntry(mj, nextEntry);
        }
    }

    // MODIFIES: mj
    // EFFECTS: parses entry from JSON object and adds it to mood journal
    private void addEntry(MoodJournal mj, JSONObject jsonObject) {
        Date date = new Date(jsonObject.getLong("date"));
        Integer mood = jsonObject.getInt("mood");
        Double sleep = jsonObject.getDouble("sleep");
        String weather = jsonObject.getString("weather");
        JournalEntry entry = new JournalEntry(date, mood, sleep, weather);
        mj.addEntry(entry);
    }



}
