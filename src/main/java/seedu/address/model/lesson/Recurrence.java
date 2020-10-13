package seedu.address.model.lesson;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

public class Recurrence {

    public static final String MESSAGE_CONSTRAINTS = "Recurrence should be an integer between 1 and 50";

    public final int value;

    public Recurrence(int value) {
        requireNonNull(value);
        checkArgument(isValidRecurrence(value), MESSAGE_CONSTRAINTS);

        this.value = value;
    }

    public static boolean isValidRecurrence(int test) {
        return test >= 1 && test <= 50;
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Recurrence // instanceof handles nulls
                && value == ((Recurrence) other).value); // state check
    }

    @Override
    public int hashCode() {
        return value;
    }
}
