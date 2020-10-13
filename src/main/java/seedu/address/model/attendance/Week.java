package seedu.address.model.attendance;

import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Objects;

/**
 * Represents a week number.
 * Guarantees: immutable; is valid as declared in {@link #isValidWeek(int)}
 */
public class Week {

    public static final String MESSAGE_CONSTRAINTS =
            "The week number must be an integer between 1 to 50.";
    public static final int LOWER_WEEK_BOUND = 1;
    public static final int UPPER_WEEK_BOUND = 50;
    private final int week;

    /**
     * Constructor method.
     * Checks that week is valid.
     */
    public Week(int week) {
        checkArgument(isValidWeek(week), MESSAGE_CONSTRAINTS);

        this.week = week;
    }

    /**
     * Returns true if the given week is an integer between 1 and 50.
     */
    public static boolean isValidWeek(int week) {
        return LOWER_WEEK_BOUND <= week && week <= UPPER_WEEK_BOUND;
    }

    public int getWeekNumber() {
        return week;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Week // instanceof handles nulls
                && ((Week) other).week == week);
    }

    @Override
    public int hashCode() {
        return Objects.hash(week);
    }
}
