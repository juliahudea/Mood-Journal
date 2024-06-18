package model;

import org.json.JSONObject;
import persistence.Writable;

import java.util.Date;


/**
 * Represents a single journal entry having a date, a mood, amount of sleep and weather
 * The class provides methods to edit and retrieve information stored in the journal entry.
  */
public class JournalEntry implements Writable {
    private Date date;           // date journal entry is created
    private int mood;           // mood on this day from 1 (being sad) to 5 (being happy)
    private double sleep;       // hours of sleep on this day
    private String weather;    // weather on this day

    // EFFECTS: date of the journal entry is set to today's date, mood in
    //          journal entry is set to mood, sleep is set to
    public JournalEntry(Date date, int mood, double sleep, String weather) {
        this.date = date;
        this.mood = mood;
        this.sleep = sleep;
        this.weather = weather;
        EventLog.getInstance().logEvent(new Event("Created entry."));
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getMood() {
        return mood;
    }

    // REQUIRES: 0 < mood < 6
    public void setMood(int mood) {
        this.mood = mood;
    }

    public double getSleep() {
        return sleep;
    }

    public void setSleep(double sleep) {
        this.sleep = sleep;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("date", this.date.getTime());
        json.put("mood", this.mood);
        json.put("sleep", this.sleep);
        json.put("weather", this.weather);
        return json;
    }
}
