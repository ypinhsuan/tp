package seedu.address.model.lesson;

import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents the number of times a lesson occurs.
 * Guarantees: immutable; is valid as declared in {@link #isValidNumberOfOccurrences(int)}
 */
public class NumberOfOccurrences {

    public static final String MESSAGE_CONSTRAINTS = "Number of occurrences should be an integer between 1 and 50.";

    public final int value;

    /**
     * Constructs a {@code NumberOfOccurrences}.
     *
     * @param value A valid number of occurrences.
     */
    public NumberOfOccurrences(int value) {
        checkArgument(isValidNumberOfOccurrences(value), MESSAGE_CONSTRAINTS);

        this.value = value;
    }

    /**
     * Returns true if the given value is between 1 and 50.
     */
    public static boolean isValidNumberOfOccurrences(int value) {
        return value >= 1 && value <= 50;
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NumberOfOccurrences // instanceof handles nulls
                && value == ((NumberOfOccurrences) other).value); // state check
    }

    @Override
    public int hashCode() {
        return value;
    }
}
