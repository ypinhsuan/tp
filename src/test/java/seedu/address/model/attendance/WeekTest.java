package seedu.address.model.attendance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

class WeekTest {

    private static final Week WEEK_ONE = new Week(1);

    @Test
    public void constructor_invalidWeek_throwsIllegalArgumentException() {
        int invalidWeek = 1000;
        assertThrows(IllegalArgumentException.class, () -> new Week(invalidWeek));
    }

    @Test
    public void isValidWeek() {
        // invalid weeks
        assertFalse(Week.isValidWeek(Week.LOWER_BOUND - 1));
        assertFalse(Week.isValidWeek(Week.UPPER_BOUND + 1));
        assertFalse(Week.isValidWeek(-1));
        assertFalse(Week.isValidWeek(0));

        // valid weeks
        assertTrue(Week.isValidWeek(Week.LOWER_BOUND));
        assertTrue(Week.isValidWeek(Week.UPPER_BOUND));
        assertTrue(Week.isValidWeek(10));
    }

    @Test
    public void getZeroBasedWeekIndex() {
        Week week = new Week(3);
        assertEquals(2, week.getZeroBasedWeekNumber());
    }

    @Test
    public void equals() {
        // same object -> returns true
        assertTrue(WEEK_ONE.equals(WEEK_ONE));

        // null -> returns false
        assertFalse(WEEK_ONE.equals(null));

        // different type -> returns false
        assertFalse(WEEK_ONE.equals(WEEK_ONE.getWeekNumber()));

        // same week -> returns true
        assertTrue(WEEK_ONE.equals(new Week(WEEK_ONE.getWeekNumber())));

        // different week -> returns false
        assertFalse(WEEK_ONE.equals(new Week(WEEK_ONE.getWeekNumber() + 1)));
    }
}
