package model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

class ActivityTest {

    @Test
    void testActivityCreation() {
        LocalDateTime date = LocalDateTime.now();
        Activity activity = new Activity(
                "1",
                date,
                "Running",
                1,
                30,
                0,
                5.0,
                120,
                160,
                "Park",
                "Nice run",
                "Sunny",
                10.0,
                "4:30",
                LocalDateTime.now(),
                LocalDateTime.now(),
                "john_doe"
        );

        assertNotNull(activity);
        assertEquals("1", activity.getId());
        assertEquals(date, activity.getDate());
        assertEquals("Running", activity.getType());
        assertEquals(1, activity.getDurationHours());
        assertEquals(30, activity.getDurationMins());
        assertEquals(0, activity.getDurationSecs());
        assertEquals(5.0, activity.getDistance());
        assertEquals(120, activity.getPulseAve());
        assertEquals(160, activity.getPulseMax());
        assertEquals("Park", activity.getLocation());
        assertEquals("Nice run", activity.getNotes());
        assertEquals("Sunny", activity.getWeather());
        assertEquals(10.0, activity.getSpeedAve());
        assertEquals("4:30", activity.getPaceAve());
        assertNotNull(activity.getCreated());
        assertNotNull(activity.getModified());
        assertEquals("john_doe", activity.getOwner());
    }

    @Test
    void testToString() {
        LocalDateTime date = LocalDateTime.now();
        Activity activity = new Activity(
                "1",
                date,
                "Running",
                1,
                30,
                0,
                5.0,
                120,
                160,
                "Park",
                "Skate Skiing",
                "Sunny",
                10.0,
                "4:30",
                LocalDateTime.now(),
                LocalDateTime.now(),
                "john_doe"
        );

        assertEquals("Activity[id=1, date = '" + date + "', type='Running', durationHours =1 ,durationMins =30 ,durationSecs =0 , distance = 5.0, pulseAve = 120, pulseMax=160,  location =Park, notes=Skate Skiing , weather =Sunny, speedAve =10.0, paceAve =4:30, created='" + activity.getCreated() + "', modified='" + activity.getModified() + "', owner='john_doe']", activity.toString());
    }
}
