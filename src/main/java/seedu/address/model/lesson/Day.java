package seedu.address.model.lesson;

import seedu.address.model.lesson.exceptions.InvalidDayException;

/**
 * Represents the days in a week.
 */
public enum Day {
    MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY;

    public static final String MESSAGE_CONSTRAINTS = "Day must be typed out fully in capital letters.\n"
            + "For example, only 'MONDAY' and not 'Monday' or 'mon' is allowed.";

    public static Day createDay(String stringDay) throws InvalidDayException {
        String capitalisedDay = stringDay.toUpperCase();
        for (Day day: values()) {
            if (day.toString().equals(capitalisedDay)) {
                return day;
            }
        }
        throw new InvalidDayException();
    }
}
