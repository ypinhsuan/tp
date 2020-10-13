package seedu.address.model.lesson;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * Represents a Lesson.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Lesson {

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    private final LocalTime startTime;
    private final LocalTime endTime;
    private final Day day;
    private final Recurrence recurrence;
    private final Location location;

    /**
     * Every field must be present and not null.
     * Creates a new lesson with the specified parameters.
     */
    public Lesson(LocalTime startTime, LocalTime endTime, Day day, Recurrence recurrence, Location location) {
        requireAllNonNull(startTime, endTime, recurrence, location, day);

        this.startTime = startTime;
        this.endTime = endTime;
        this.day = day;
        this.recurrence = recurrence;
        this.location = location;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public Day getDay() {
        return day;
    }

    public Recurrence getRecurrence() {
        return recurrence;
    }

    public Location getLocation() {
        return location;
    }



    /**
     * Returns true if both lessons have the same start time, end time, day and location.
     * This defines a weaker notion of equality between two lessons.
     */
    public boolean isSameLesson(Lesson otherLesson) {
        if (otherLesson == this) {
            return true;
        }

        return otherLesson != null
                && otherLesson.getStartTime().equals(getStartTime())
                && otherLesson.getEndTime().equals(getStartTime())
                && otherLesson.getDay().equals(getDay())
                && otherLesson.getLocation().equals(getLocation());
    }

    /**
     * Returns true if both lessons have same start time, end time, day, recurrence and location.
     * This defines a stronger notion of equality between two lessons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Lesson)) {
            return false;
        }

        Lesson otherLesson = (Lesson) other;

        return otherLesson.getStartTime().equals(getStartTime())
                && otherLesson.getEndTime().equals(getStartTime())
                && otherLesson.getDay().equals(getDay())
                && otherLesson.getRecurrence().equals(getRecurrence())
                && otherLesson.getLocation().equals(getLocation());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(startTime, endTime, day, recurrence, location);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(day)
                .append(" Time: ")
                .append(TIME_FORMATTER.format(getStartTime()))
                .append(" to ")
                .append(TIME_FORMATTER.format(getEndTime()))
                .append(" Location: ")
                .append(getLocation());
        return builder.toString();
    }
}
