package seedu.address.model.lesson;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class VenueTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Venue(null));
    }

    @Test
    public void constructor_invalidVenue_throwsIllegalArgumentException() {
        String invalidVenue = "";
        assertThrows(IllegalArgumentException.class, () -> new Venue(invalidVenue));
    }

    @Test
    public void isValidVenue() {
        // null venue
        assertThrows(NullPointerException.class, () -> Venue.isValidVenue(null));

        // blank venue
        assertFalse(Venue.isValidVenue("")); // empty string
        assertFalse(Venue.isValidVenue(" ")); // spaces only

        // invalid venues
        assertFalse(Venue.isValidVenue("COM1#")); // special characters
        assertFalse(Venue.isValidVenue(" COM1")); // leading space

        // valid venues
        assertTrue(Venue.isValidVenue("COM1_02-11")); // mixture of alphanumeric and special characters
        assertTrue(Venue.isValidVenue("Com2")); // alphabets only
        assertTrue(Venue.isValidVenue("0209")); // numbers only
        assertTrue(Venue.isValidVenue("COM2 0101")); // alphanumeric with spaces
    }
}
