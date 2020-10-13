package seedu.address.model.lesson;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class NumberOfOccurrencesTest {

    @Test
    public void constructor_invalidNumberOfOccurrences_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new NumberOfOccurrences(0));
        assertThrows(IllegalArgumentException.class, () -> new NumberOfOccurrences(51));
    }

    @Test
    public void isValidNumberOfOccurrences() {
        // invalid number of occurrences
        assertFalse(NumberOfOccurrences.isValidNumberOfOccurrences(-1));
        assertFalse(NumberOfOccurrences.isValidNumberOfOccurrences(100));

        // valid number of occurrences
        assertTrue(NumberOfOccurrences.isValidNumberOfOccurrences(1));
        assertTrue(NumberOfOccurrences.isValidNumberOfOccurrences(14));
    }
}
