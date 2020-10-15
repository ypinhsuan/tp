package seedu.address.model.lesson;

import seedu.address.model.lesson.exceptions.InvalidDayException;

/**
 * Represents the days in a week.
 */
public enum Day {
    MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY;

    public static final String MESSAGE_CONSTRAINTS = "Day must be typed out fully in capital letters.\n"
            + "For example, only 'MONDAY' and not 'Monday' or 'mon' is allowed.";

    /**
     * Creates a {@code Day} object based on the given {@code String}.
     * The {@code String} must be capitalized and be spelt exactly as each enum type.
     *
     * @throws InvalidDayException if the {@code String} does not match any of the enum types.
     */
    public static Day createDay(String stringDay) throws InvalidDayException {
        for (Day day: values()) {
            if (day.toString().equals(stringDay)) {
                return day;
            }
        }
        throw new InvalidDayException();
    }
}
