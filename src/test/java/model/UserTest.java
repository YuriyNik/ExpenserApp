package model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void testUserCreation() {
        User user = new User("john_doe", "password", "ROLE_USER");
        assertNotNull(user);
        assertNull(user.getId());
        assertEquals("john_doe", user.getUsername());
        assertEquals("password", user.getPassword());
        assertArrayEquals(new String[]{"ROLE_USER"}, user.getRoles());
        assertNull(user.getCreated());
        assertNull(user.getModified());
    }

    @Test
    void testUserModification() {
        User user = new User("john_doe", "password", "ROLE_USER");
        LocalDateTime created = LocalDateTime.now().minusDays(1);
        LocalDateTime modified = LocalDateTime.now();

        user.setCreated(created);
        user.setModified(modified);
        user.setExpenceTypes(new String[]{"Food", "Travel"});
        user.setCurrencyTypes(new String[]{"USD", "EUR"});
        user.setTodoLabels(new String[]{"Personal", "Work"});
        user.setActivityLabels(new String[]{"Running", "Cycling"});
        user.setActivityPlaces(new String[]{"Park", "Gym"});

        assertEquals(created, user.getCreated());
        assertEquals(modified, user.getModified());
        assertArrayEquals(new String[]{"Food", "Travel"}, user.getExpenceTypes());
        assertArrayEquals(new String[]{"USD", "EUR"}, user.getCurrencyTypes());
        assertArrayEquals(new String[]{"Personal", "Work"}, user.getTodoLabels());
        assertArrayEquals(new String[]{"Running", "Cycling"}, user.getActivityLabels());
        assertArrayEquals(new String[]{"Park", "Gym"}, user.getActivityPlaces());
    }

    @Test
    void testToString() {
        User user = new User("john_doe", "password", "ROLE_USER");
        assertEquals("{id=null, username='john_doe', roles='[ROLE_USER]',expenceTypes='null', currencyTypes='null',activityLabels='null',todoLabels='null',activityPlaces='null'}", user.toString());
    }
}

