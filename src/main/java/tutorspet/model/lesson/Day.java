package tutorspet.model.lesson;

import tutorspet.model.lesson.exceptions.InvalidDayException;

/**
 * Represents the days in a week.
 */
public enum Day {
    MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY;

    public static final String MESSAGE_CONSTRAINTS = "Days should be spelt out in full.\n"
            + "For example, only 'Monday' and not 'mon' is allowed.";

    /**
     * Creates a {@code Day} object based on the given {@code String}.
     * The {@code String} must be capitalized and be spelt exactly as each enum value.
     *
     * @throws InvalidDayException if the {@code String} does not match any of the enum values.
     */
    public static Day createDay(String stringDay) throws InvalidDayException {
        for (Day day : values()) {
            if (day.name().equals(stringDay.toUpperCase())) {
                return day;
            }
        }
        throw new InvalidDayException();
    }

    @Override
    public String toString() {
        return name().substring(0, 1) + name().substring(1).toLowerCase();
    }
}
